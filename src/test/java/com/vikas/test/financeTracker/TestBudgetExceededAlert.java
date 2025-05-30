package com.vikas.test.financeTracker;

import com.vikas.financetracker.dto.BudgetResponse;

import java.math.BigDecimal;

public class TestBudgetExceededAlert {

    @Test
    void testBudgetExceededAlertGeneration() {
        BudgetResponse budget = new BudgetResponse("Food", 2025, 5,
                new BigDecimal("1000"), new BigDecimal("1300"));

        Mockito.when(budgetService.getBudgetStatus("user1", 2025, 5))
                .thenReturn(Flux.just(budget));

        Mockito.when(alertRepository.save(Mockito.any(Alert.class)))
                .thenReturn(Mono.just(new Alert()));

        StepVerifier.create(alertService.checkAndGenerateAlerts("user1", 2025, 5))
                .verifyComplete();
    }

}
