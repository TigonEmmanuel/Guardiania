package com.guardianai.ai.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Component
public class OpenRouterClient {

    private final WebClient client;
    private final String apiKey;
    private final String model;

        @SuppressWarnings("null")
        public OpenRouterClient(
            @Value("${guardian.openrouter.baseUrl:https://api.openrouter.ai}") @Nullable String baseUrl,
            @Value("${guardian.openrouter.apiKey:}") @Nullable String apiKey,
            @Value("${guardian.openrouter.model:gpt-4o-mini}") @Nullable String model
        ) {
        // Normalize potential null injected values to safe defaults to satisfy null-safety
        String safeBase = Objects.requireNonNullElse(baseUrl, "https://api.openrouter.ai");
        this.client = WebClient.builder().baseUrl(safeBase).build();
        this.apiKey = Objects.requireNonNullElse(apiKey, "");
        this.model = Objects.requireNonNullElse(model, "gpt-4o-mini");
    }

    public String safeRewrite(String text) {
        if (apiKey == null || apiKey.isEmpty()) {
            return text;  // No API key, return original text
        }

        Map<String, Object> body = Map.of(
            "model", this.model,
            "input", text
        );

        // ensure non-null values for API (satisfy null-safety analysis)
        var mediaType = Objects.requireNonNull(MediaType.APPLICATION_JSON);
        Map<String, Object> safeBody = Objects.requireNonNull(body);

        Mono<Map<String, Object>> resp = client.post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(mediaType)
                .bodyValue(safeBody)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {});

        Map<String, Object> result = resp.block();

        if (result == null) return text;

        Object out = result.get("output");
        return (out != null) ? out.toString() : text;
    }
}
