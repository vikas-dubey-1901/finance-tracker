package com.vikas.financetracker.service;

import com.example.reactivefinancetracker.domain.Transaction;
import com.example.reactivefinancetracker.dto.TransactionRequest;
import com.example.reactivefinancetracker.dto.TransactionResponse;
import com.example.reactivefinancetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Mono<TransactionResponse> addTransaction(String userId, TransactionRequest request) {
        Transaction transaction = Transaction.builder()
                .userId(userId)
                .category(request.getCategory())
                .amount(request.getAmount())
                .type(request.getType())
                .description(request.getDescription())
                .timestamp(Instant.now())
                .build();

        return transactionRepository.save(transaction)
                .map(this::toResponse);
    }

    public Flux<TransactionResponse> getTransactionsForUser(String userId) {
        return transactionRepository.findAllByUserId(userId)
                .map(this::toResponse);
    }

    public Mono<Void> deleteTransaction(String userId, String transactionId) {
        return transactionRepository.findById(transactionId)
                .filter(tx -> tx.getUserId().equals(userId))
                .flatMap(transactionRepository::delete);
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .category(transaction.getCategory())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .description(transaction.getDescription())
                .type(transaction.getType())
                .build();
    }
}

