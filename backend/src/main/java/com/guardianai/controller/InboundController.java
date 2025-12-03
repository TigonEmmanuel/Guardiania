package com.guardianai.controller;

import com.guardianai.service.SafetyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class InboundController {

    private final SafetyService safetyService;

    public InboundController(SafetyService safetyService) {
        this.safetyService = safetyService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<Map<String, Object>> ingest(@RequestBody Map<String, Object> payload) {
        Map<String, Object> result = safetyService.processIncoming(payload);
        return ResponseEntity.ok(result);
    }
}
