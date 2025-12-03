package com.guardianai.db.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "audit_logs")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String original;

    @Column(columnDefinition = "text")
    private String sanitized;

    private boolean piiDetected;
    private boolean nsfwDetected;
    private boolean profanityDetected;
    private boolean customKeywordDetected;
    private boolean hateSpeechDetected;
    private boolean spamDetected;
    private boolean violenceDetected;
    private Instant timestamp;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOriginal() { return original; }
    public void setOriginal(String original) { this.original = original; }
    public String getSanitized() { return sanitized; }
    public void setSanitized(String sanitized) { this.sanitized = sanitized; }
    public boolean isPiiDetected() { return piiDetected; }
    public void setPiiDetected(boolean piiDetected) { this.piiDetected = piiDetected; }
    public boolean isNsfwDetected() { return nsfwDetected; }
    public void setNsfwDetected(boolean nsfwDetected) { this.nsfwDetected = nsfwDetected; }
    public boolean isProfanityDetected() { return profanityDetected; }
    public void setProfanityDetected(boolean profanityDetected) { this.profanityDetected = profanityDetected; }
    public boolean isCustomKeywordDetected() { return customKeywordDetected; }
    public void setCustomKeywordDetected(boolean customKeywordDetected) { this.customKeywordDetected = customKeywordDetected; }
    public boolean isHateSpeechDetected() { return hateSpeechDetected; }
    public void setHateSpeechDetected(boolean hateSpeechDetected) { this.hateSpeechDetected = hateSpeechDetected; }
    public boolean isSpamDetected() { return spamDetected; }
    public void setSpamDetected(boolean spamDetected) { this.spamDetected = spamDetected; }
    public boolean isViolenceDetected() { return violenceDetected; }
    public void setViolenceDetected(boolean violenceDetected) { this.violenceDetected = violenceDetected; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
