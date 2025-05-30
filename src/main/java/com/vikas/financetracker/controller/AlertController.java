package com.vikas.financetracker.controller;

import com.example.reactivefinancetracker.domain.Alert;
import com.example.reactivefinancetracker.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public Flux<Alert> getUserAlerts(Authentication authentication) {
        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
        return alertService.getUserAlerts(userId);
    }

    @PostMapping("/check")
    public Mono<Void> triggerAlertCheck(@RequestParam int year,
                                        @RequestParam int month,
                                        Authentication authentication) {
        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
        return alertService.checkAndGenerateAlerts(userId, year, month);
    }
}

