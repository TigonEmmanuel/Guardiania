package com.guardianai.ai.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guardianai.ai.model.User;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
