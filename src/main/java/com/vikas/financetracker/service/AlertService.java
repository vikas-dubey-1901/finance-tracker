package com.vikas.financetracker.service;

import com.example.reactivefinancetracker.domain.Alert;
import com.example.reactivefinancetracker.dto.BudgetResponse;
import com.example.reactivefinancetracker.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final BudgetService budgetService;
    private final AlertRepository alertRepo;

    public Mono<Void> checkAndGenerateAlerts(String userId, int year, int month) {
        return budgetService.getBudgetStatus(userId, year, month)
                .filter(b -> b.getSpent().compareTo(b.getBudgeted()) > 0)
                .flatMap(b -> alertRepo.save(Alert.builder()
                        .userId(userId)
                        .category(b.getCategory())
                        .year(year)
                        .month(month)
                        .message("You exceeded your budget for " + b.getCategory() +
                                ": ₹" + b.getSpent() + " spent vs ₹" + b.getBudgeted() + " budgeted.")
                        .createdAt(Instant.now())
                        .build()))
                .then(); // Return Mono<Void>
    }

    public Flux<Alert> getUserAlerts(String userId) {
        return alertRepo.findAllByUserIdOrderByCreatedAtDesc(userId);
    }
}

