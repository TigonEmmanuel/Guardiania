package com.guardianai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WorkflowController {

    @GetMapping("/workflow")
    public ResponseEntity<?> getWorkflow() {

        Map<String, Object> sampleWorkflow = Map.of(
            "status", "OK",
            "description", "AI Guardrail Workflow",
            "steps", new String[]{
                    "Receive Input",
                    "Check Safety",
                    "Run Agents",
                    "Generate Output"
            }
        );

        return ResponseEntity.ok(sampleWorkflow);
    }
}
