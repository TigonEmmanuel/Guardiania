package com.guardianai.db.repo;

import com.guardianai.db.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntry, Long> {
}
