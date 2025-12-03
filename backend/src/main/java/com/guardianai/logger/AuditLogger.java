package com.guardianai.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Map;

public class AuditLogger {
    private static final Logger log = LoggerFactory.getLogger(AuditLogger.class);

    public static void logEvent(Map<String, Object> event) {
        log.info("AUDIT {} | {}", Instant.now().toString(), event);
    }
}
