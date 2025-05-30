package com.vikas.financetracker.service;

import com.example.reactivefinancetracker.domain.Budget;
import com.example.reactivefinancetracker.domain.Transaction;
import com.example.reactivefinancetracker.dto.BudgetRequest;
import com.example.reactivefinancetracker.dto.BudgetResponse;
import com.example.reactivefinancetracker.repository.BudgetRepository;
import com.example.reactivefinancetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepo;
    private final TransactionRepository transactionRepo;

    public Mono<Budget> setBudget(String userId, BudgetRequest request) {
        return budgetRepo.findByUserIdAndCategoryAndYearAndMonth(userId,
                        request.getCategory(), request.getYear(), request.getMonth())
                .flatMap(existing -> {
                    existing.setAmount(request.getAmount());
                    return budgetRepo.save(existing);
                })
                .switchIfEmpty(budgetRepo.save(Budget.builder()
                        .userId(userId)
                        .category(request.getCategory())
                        .year(request.getYear())
                        .month(request.getMonth())
                        .amount(request.getAmount())
                        .build()));
    }

    public Flux<BudgetResponse> getBudgetStatus(String userId, int year, int month) {
        return budgetRepo.findAllByUserIdAndYearAndMonth(userId, year, month)
                .flatMap(budget ->
                        transactionRepo.findAllByUserId(userId)
                                .filter(tx -> tx.getType() == Transaction.TransactionType.EXPENSE)
                                .filter(tx -> {
                                    var date = tx.getTimestamp().atZone(ZoneId.systemDefault()).toLocalDate();
                                    return date.getYear() == year &&
                                            date.getMonthValue() == month &&
                                            tx.getCategory().equalsIgnoreCase(budget.getCategory());
                                })
                                .map(Transaction::getAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .map(spent -> BudgetResponse.builder()
                                        .category(budget.getCategory())
                                        .year(year)
                                        .month(month)
                                        .budgeted(budget.getAmount())
                                        .spent(spent)
                                        .build())
                );
    }
}

