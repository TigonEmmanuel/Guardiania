package com.guardianai;

import com.guardianai.ai.client.OpenRouterClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class OpenRouterClientE2ETest {

    private MockWebServer server;

    @BeforeEach
    public void start() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    public void stop() throws IOException {
        server.shutdown();
    }

    @Test
    public void testSafeRewriteWithMockServer() {
        String body = "{ \"choices\": [ { \"message\": { \"content\": \"mocked-rewrite\" } } ] }";
        server.enqueue(new MockResponse().setBody(body).setHeader("Content-Type", "application/json"));

        String baseUrl = server.url("").toString();
        OpenRouterClient client = new OpenRouterClient(baseUrl, "fake-key", "gpt-test");

        String out = client.safeRewrite("input text");
        assertEquals("mocked-rewrite", out);
    }
}
