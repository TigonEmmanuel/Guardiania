package com.guardianai.ai.auth;

import com.guardianai.ai.model.User;
import com.guardianai.ai.repository.userRepository;
import com.guardianai.ai.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(userRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User authenticate(String username, String rawPassword) {

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;

        // ✅ THIS IS THE KEY LINE
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return null;
        }

        return user;
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
