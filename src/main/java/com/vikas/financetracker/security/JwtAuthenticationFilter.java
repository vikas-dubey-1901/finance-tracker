package com.vikas.financetracker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    private final JwtUtils jwtUtils;
    private final ReactiveUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(ReactiveUserDetailsService userDetailsService, JwtUtils jwtUtils) {
        super(new JwtReactiveAuthenticationManager(userDetailsService, jwtUtils));
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;

        setServerAuthenticationConverter(new JwtServerAuthenticationConverter(jwtUtils, userDetailsService));
        setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
    }
}

class JwtReactiveAuthenticationManager implements org.springframework.security.authentication.ReactiveAuthenticationManager {

    private final ReactiveUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public JwtReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<org.springframework.security.core.Authentication> authenticate(org.springframework.security.core.Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        if (!jwtUtils.validateJwtToken(authToken)) {
            return Mono.empty();
        }

        String username = jwtUtils.getUsernameFromJwtToken(authToken);
        return userDetailsService.findByUsername(username)
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }
}

class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtUtils jwtUtils;
    private final ReactiveUserDetailsService userDetailsService;

    public JwtServerAuthenticationConverter(JwtUtils jwtUtils, ReactiveUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<org.springframework.security.core.Authentication> convert(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            return Mono.just(new UsernamePasswordAuthenticationToken(null, authToken));
        }
        return Mono.empty();
    }
}

