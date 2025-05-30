package com.vikas.financetracker.config;

import com.vikas.financetracker.security.JwtAuthenticationFilter;
import com.vikas.financetracker.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/api/v1/auth/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(jwtAuthenticationFilter, ServerHttpSecurity.SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
