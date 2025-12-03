package com.guardianai.db.service;

import com.guardianai.db.entity.LogEntry;
import com.guardianai.db.repo.LogRepository;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class LogService {
    private final LogRepository repo;
    public LogService(LogRepository repo) { this.repo = repo; }

    public LogEntry save(LogEntry e) {
        Objects.requireNonNull(e);
        return Objects.requireNonNull(repo.save(e));
    }

    public long count() {
        return repo.count();
    }
}
