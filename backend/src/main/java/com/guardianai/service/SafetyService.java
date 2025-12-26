package com.guardianai.service;

import com.guardianai.ai.client.OpenRouterClient;
import com.guardianai.core.detectors.NSFWDetector;
import com.guardianai.core.detectors.PIIDetector;
import com.guardianai.core.sanitizers.Sanitizer;
import com.guardianai.core.reassembly.Reassembler;
import com.guardianai.db.entity.LogEntry;
import com.guardianai.db.service.LogService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class SafetyService {

    private final OpenRouterClient openRouterClient;
    private final LogService logService;

    public SafetyService(OpenRouterClient openRouterClient, LogService logService) {
        this.openRouterClient = openRouterClient;
        this.logService = logService;
    }

    public Map<String, Object> processIncoming(Map<String, Object> payload) {
        String message = (String) payload.getOrDefault("message", "");
        Map<String, Object> report = new HashMap<>();
        report.put("original", message);

            boolean pii = PIIDetector.containsPII(message);
            boolean nsfw = NSFWDetector.isNSFW(message);
            boolean profanity = Sanitizer.sanitizeProfanity(message).contains("[PROFANITY]");
            boolean customKeyword = Sanitizer.sanitizeCustomKeywords(message).contains("[REDACTED]");

            // Basic hate speech, spam, violence detection (simple keyword-based)
            boolean hateSpeech = message.toLowerCase().matches(".*(hate|racist|bigot|kill|destroy|terrorist).*" );
            boolean spam = message.toLowerCase().matches(".*(buy now|free money|click here|subscribe).*" );
            boolean violence = message.toLowerCase().matches(".*(attack|shoot|stab|bomb|fight).*" );

            String cleaned = message;
            cleaned = Sanitizer.maskKeys(cleaned);
            cleaned = Sanitizer.redactUrls(cleaned);
            cleaned = Sanitizer.maskPII(cleaned);
            cleaned = Sanitizer.sanitizeNSFW(cleaned);
            cleaned = Sanitizer.sanitizeProfanity(cleaned);
            cleaned = Sanitizer.sanitizeCustomKeywords(cleaned);

            // Reassemble (simple strategy: sanitized text)
            String finalPrompt = Reassembler.merge(message, cleaned);

            // Optionally send to LLM for rewrite if strictMode requires
            String llmResponse = null;
            try {
                llmResponse = openRouterClient.safeRewrite(finalPrompt);
            } catch (Exception e) {
                // fallback: use sanitized prompt
                llmResponse = finalPrompt;
            }

            report.put("sanitized", cleaned);
            report.put("llmRewrite", llmResponse);
            report.put("blocked", pii || nsfw || profanity || customKeyword || hateSpeech || spam || violence);
            report.put("timestamp", Instant.now().toString());
            report.put("detections", Map.of(
                "pii", pii,
                "nsfw", nsfw,
                "profanity", profanity,
                "customKeyword", customKeyword,
                "hateSpeech", hateSpeech,
                "spam", spam,
                "violence", violence
            ));

            // Log event (persist if a LogService is available)
            LogEntry entry = new LogEntry();
            entry.setOriginal(message);
            entry.setSanitized(cleaned);
            entry.setPiiDetected(pii);
            entry.setNsfwDetected(nsfw);
            entry.setProfanityDetected(profanity);
            entry.setCustomKeywordDetected(customKeyword);
            entry.setHateSpeechDetected(hateSpeech);
            entry.setSpamDetected(spam);
            entry.setViolenceDetected(violence);
            entry.setTimestamp(Instant.now());
            if (logService != null) {
                logService.save(entry);
            }

            return report;
    }
}
