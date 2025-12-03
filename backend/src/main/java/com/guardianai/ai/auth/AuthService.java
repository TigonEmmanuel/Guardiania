package com.guardianai.ai.Auth;

import com.guardianai.ai.Repository.UserRepository;
import com.guardianai.ai.model.User;
import com.guardianai.ai.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authenticate user credentials and return JWT if valid.
     *
     * @param username username
     * @param rawPassword raw password to check
     * @return a JWT token string if credentials are valid, otherwise null
     */
    public String authenticateAndGenerateToken(String username, String rawPassword) {
        Optional<User> opt = userRepository.findByUsername(username);
        if (opt.isEmpty()) return null;

        User user = opt.get();
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        return null;
    }

    /**
     * Optional helper: get user by username.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
