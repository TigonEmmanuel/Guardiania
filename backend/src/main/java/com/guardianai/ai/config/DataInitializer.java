package com.guardianai.ai.config;

import com.guardianai.ai.model.User;
import com.guardianai.ai.repository.userRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createTestAdmin(userRepository repo, PasswordEncoder encoder) {
        return args -> {
            String admin = "root";
            if (!repo.existsByUsername(admin)) {
                User u = new User();
                u.setUsername(admin);
                u.setPassword(encoder.encode("emmanuel"));
                u.setRole("ADMIN");
                repo.save(u);
                System.out.println("Created test admin user: root / emmanuel");
            }
        };
    }
}
