package com.guardianai.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Deprecated
public class LogService {

    public List<Object> getAllLogs() {
        return Collections.emptyList();
    }

    public void addLog(Object log) {
        // deprecated in-memory log service; no-op
    }
}
