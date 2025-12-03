package com.guardianai.service;

import com.guardianai.model.AuditLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    private final List<AuditLog> logs = new ArrayList<>();

    public List<AuditLog> getAllLogs() {
        return logs;
    }

    public void addLog(AuditLog log) {
        logs.add(log);
    }
}
