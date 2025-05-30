package com.vikas.financetracker.controller;

import com.example.reactivefinancetracker.dto.TransactionRequest;
import com.example.reactivefinancetracker.dto.TransactionResponse;
import com.example.reactivefinancetracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionResponse> addTransaction(@Validated @RequestBody TransactionRequest request,
                                                    Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        // In a real app, you would fetch userId by username from DB or token claims
        // For now, assume username == userId for simplicity (or implement a user service)
        return transactionService.addTransaction(username, request);
    }

    @GetMapping
    public Flux<TransactionResponse> getTransactions(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return transactionService.getTransactionsForUser(username);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTransaction(@PathVariable String id, Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return transactionService.deleteTransaction(username, id);
    }
}

