package com.guardianai.ai.security;

import com.guardianai.ai.model.User;
import com.guardianai.ai.repository.userRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final userRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, userRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null) {
            try {
                // Use the actual JwtUtil methods you have
                String username = jwtUtil.extractUsername(token);

                if (username != null && !jwtUtil.isTokenExpired(token)
                        && SecurityContextHolder.getContext().getAuthentication() == null) {

                    Optional<User> userOpt = userRepository.findByUsername(username);

                    if (userOpt.isPresent()) {
                        User user = userOpt.get();

                        // Example: take role from user.getRole() if you store roles
                        List<SimpleGrantedAuthority> authorities = 
        List.of(new SimpleGrantedAuthority("ROLE_USER"));

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        user.getUsername(),
                                        null,
                                        authorities
                                );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception ex) {
                // token invalid or parsing error — do not authenticate
                logger.debug("JWT processing failed: " + ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
