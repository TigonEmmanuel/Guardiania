package com.guardianai;

import com.guardianai.ai.client.OpenRouterClient;
import com.guardianai.ai.client.OpenRouterResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OpenRouterClientTest {

    @Test
    public void testExtractTextFromVariousResponses() throws Exception {
        // Test OpenRouterResponse extraction helper
        OpenRouterResponse r1 = new OpenRouterResponse();
        r1.output = "hello-output";
        assertEquals("hello-output", r1.extractText());

        OpenRouterResponse r2 = new OpenRouterResponse();
        r2.choices = List.of(Map.of("text", "choice-text"));
        assertEquals("choice-text", r2.extractText());

        OpenRouterResponse r3 = new OpenRouterResponse();
        r3.choices = List.of(Map.of("message", Map.of("content", "msg-content")));
        assertEquals("msg-content", r3.extractText());
    }
}
