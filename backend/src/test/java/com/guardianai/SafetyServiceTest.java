package com.guardianai;

import com.guardianai.service.SafetyService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SafetyServiceTest {

    @Test
    public void testSanitizeKeyAndUrl() {
        SafetyService s = new SafetyService(null, null);
        Map<String,Object> p = new HashMap<>();
        p.put("message", "Hello sk-ABCDEFGHIJKLMNOPQRSTUV https://example.com");
        Map<String,Object> r = s.processIncoming(p);
        assertTrue(r.get("sanitized").toString().contains("[KEY_REMOVED]"));
        assertTrue(r.get("sanitized").toString().contains("[URL_REDACTED]"));
    }
}
