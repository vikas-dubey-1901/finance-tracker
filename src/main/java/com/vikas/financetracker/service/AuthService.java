package com.vikas.financetracker.service;

import com.vikas.financetracker.domain.User;
import com.vikas.financetracker.dto.AuthRequest;
import com.vikas.financetracker.repository.UserRepository;
import com.vikas.financetracker.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public Mono<String> register(AuthRequest request) {
        return userRepository.existsByUsername(request.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Username is already taken"));
                    }
                    return userRepository.existsByEmail(request.getEmail());
                })
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Email is already in use"));
                    }
                    User user = User.builder()
                            .username(request.getUsername())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .build();
                    return userRepository.save(user).map(u -> "User registered successfully");
                });
    }

    public Mono<String> login(AuthRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> {
                    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        return Mono.just(jwtUtils.generateJwtToken(user.getUsername()));
                    } else {
                        return Mono.error(new RuntimeException("Invalid credentials"));
                    }
                });
    }
}


