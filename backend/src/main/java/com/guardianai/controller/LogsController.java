package com.guardianai.controller;

import com.guardianai.model.AuditLog;
import com.guardianai.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LogsController {

    private final LogService logService;

    public LogsController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLog>> getLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

    // // Optional: Add a test log so dashboard displays something
    // @PostMapping("/logs/test")
    // public ResponseEntity<?> createTestLog() {
    //     logService.addLog(
    //         new AuditLog(
    //                 1L,
    //                 "Hello world",
    //                 "Hello world",
    //                 false,
    //                 false
    //         )
    //     );

    //     return ResponseEntity.ok("Test log created");
    // }
}
