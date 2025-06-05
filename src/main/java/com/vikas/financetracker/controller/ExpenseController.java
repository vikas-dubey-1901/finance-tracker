package com.vikas.financetracker.controller;

import com.vikas.financetracker.event.ExpenseCreatedEvent;
import com.vikas.financetracker.model.Expense;
import com.vikas.financetracker.publisher.ExpenseEventPublisher;
import com.vikas.financetracker.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseEventPublisher expenseEventPublisher;

    public ExpenseController(ExpenseService expenseService, ExpenseEventPublisher expenseEventPublisher) {
        this.expenseService = expenseService;
        this.expenseEventPublisher = expenseEventPublisher;
    }

    @PostMapping
    public Mono<ResponseEntity<Expense>> createExpense(@RequestBody Expense expense) {
        return expenseService.save(expense)
                .flatMap(savedExpense -> {
                    ExpenseCreatedEvent event = new ExpenseCreatedEvent(
                            savedExpense.getId(),
                            savedExpense.getCategory(),
                            savedExpense.getAmount(),
                            Instant.now().toString(),
                            savedExpense.getUserId()
                    );
                    return expenseEventPublisher.publishExpenseCreatedEvent(event)
                            .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(savedExpense));
                });
    }
}
