package com.guardianai.ai.auth;

import com.guardianai.ai.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "username and password required"));
        }

        User user = authService.authenticate(request.getUsername(), request.getPassword());

        if (user == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Invalid username or password"));
        }

        String token = authService.generateToken(user);

        return ResponseEntity.ok(
                Map.of(
                        "user", Map.of(
                                "id", user.getId(),
                                "username", user.getUsername(),
                                "role", user.getRole()
                        ),
                        "token", token
                )
        );
    }
}
