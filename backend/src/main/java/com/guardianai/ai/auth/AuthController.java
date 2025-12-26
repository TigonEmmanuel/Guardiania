package com.guardianai.ai.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        // Dummy login ― replace with real authentication later
        if (username.equals("root") && password.equals("emmanuel")) {
            return ResponseEntity.ok(
                Map.of(
                    "user", Map.of("username", username),
                    "token", "DUMMY_JWT_TOKEN"
                )
            );
        }

        return ResponseEntity.status(401).body(
            Map.of("message", "Invalid username or password")
        );
    }
}
