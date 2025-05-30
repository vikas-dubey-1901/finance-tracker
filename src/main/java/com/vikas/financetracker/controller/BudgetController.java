package com.vikas.financetracker.controller;

import com.example.reactivefinancetracker.domain.Budget;
import com.example.reactivefinancetracker.dto.BudgetRequest;
import com.example.reactivefinancetracker.dto.BudgetResponse;
import com.example.reactivefinancetracker.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public Mono<Budget> setBudget(@Valid @RequestBody BudgetRequest request, Authentication auth) {
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        return budgetService.setBudget(username, request);
    }

    @GetMapping
    public Flux<BudgetResponse> getBudgetStatus(@RequestParam int year,
                                                @RequestParam int month,
                                                Authentication auth) {
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        return budgetService.getBudgetStatus(username, year, month);
    }
}

