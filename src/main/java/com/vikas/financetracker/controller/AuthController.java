package com.vikas.financetracker.controller;

import com.example.reactivefinancetracker.dto.AuthRequest;
import com.example.reactivefinancetracker.dto.AuthResponse;
import com.example.reactivefinancetracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@Validated @RequestBody AuthRequest request) {
        return authService.register(request)
                .map(message -> ResponseEntity.status(HttpStatus.CREATED).body(message))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Validated @RequestBody AuthRequest request) {
        return authService.login(request)
                .map(token -> ResponseEntity.ok(new AuthResponse(token)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}

