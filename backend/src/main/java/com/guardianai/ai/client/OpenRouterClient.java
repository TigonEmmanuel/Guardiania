package com.guardianai.ai.client;

import com.guardianai.ai.client.OpenRouterResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import com.guardianai.ai.client.OpenRouterRequest;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.List;

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
        String safeBase = Objects.requireNonNullElse(baseUrl, "https://api.openrouter.ai");
        this.client = WebClient.builder().baseUrl(safeBase).build();
        this.apiKey = Objects.requireNonNullElse(apiKey, "");
        this.model = Objects.requireNonNullElse(model, "gpt-4o-mini");
    }

    public String safeRewrite(String text) {
        if (apiKey == null || apiKey.isEmpty()) {
            return text;
        }

        OpenRouterRequest req = new OpenRouterRequest(this.model, text);

        var mediaType = Objects.requireNonNull(MediaType.APPLICATION_JSON);

        CircuitBreaker cb = CircuitBreaker.ofDefaults("openrouter");

        Mono<OpenRouterResponse> resp = client.post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(mediaType)
            .bodyValue(req)
                .retrieve()
                .onStatus(s -> s.isError(), cr -> cr.createException().flatMap(Mono::error))
            .bodyToMono(OpenRouterResponse.class)
            .transform(CircuitBreakerOperator.of(cb))
            .timeout(Duration.ofSeconds(5))
            .retryWhen(Retry.backoff(2, Duration.ofMillis(200)));

        OpenRouterResponse result;
        try {
            result = resp.block();
        } catch (Exception e) {
            return text;
        }

        if (result == null) return text;
        String parsed = result.extractText();
        return parsed == null ? text : parsed;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private String findAnyString(Object node) {
        if (node == null) return null;
        if (node instanceof String) return (String) node;
        if (node instanceof Map) {
            for (Object v : ((Map) node).values()) {
                String s = findAnyString(v);
                if (s != null && !s.isEmpty()) return s;
            }
        }
        if (node instanceof List) {
            for (Object v : (List) node) {
                String s = findAnyString(v);
                if (s != null && !s.isEmpty()) return s;
            }
        }
        return null;
    }
}
