package com.vikas.financetracker.service;

import com.example.reactivefinancetracker.domain.Transaction;
import com.example.reactivefinancetracker.dto.CategorySpending;
import com.example.reactivefinancetracker.dto.MonthlySpendingSummary;
import com.example.reactivefinancetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionRepository transactionRepository;

    public Flux<MonthlySpendingSummary> getMonthlySummary(String userId) {
        return transactionRepository.findAllByUserId(userId)
                .groupBy(tx -> {
                    var localDate = tx.getTimestamp().atZone(ZoneId.systemDefault()).toLocalDate();
                    return localDate.getYear() + "-" + localDate.getMonth();
                })
                .flatMap(grouped -> grouped.collectList().map(list -> {
                    int year = list.get(0).getTimestamp().atZone(ZoneId.systemDefault()).getYear();
                    Month month = list.get(0).getTimestamp().atZone(ZoneId.systemDefault()).getMonth();
                    BigDecimal income = list.stream()
                            .filter(tx -> tx.getType() == Transaction.TransactionType.INCOME)
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal expense = list.stream()
                            .filter(tx -> tx.getType() == Transaction.TransactionType.EXPENSE)
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return MonthlySpendingSummary.builder()
                            .year(year)
                            .month(month)
                            .totalIncome(income)
                            .totalExpense(expense)
                            .build();
                }));
    }

    public Flux<CategorySpending> getCategoryWiseSummary(String userId) {
        return transactionRepository.findAllByUserId(userId)
                .filter(tx -> tx.getType() == Transaction.TransactionType.EXPENSE)
                .groupBy(Transaction::getCategory)
                .flatMap(grouped -> grouped.map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .map(total -> CategorySpending.builder()
                                .category(grouped.key())
                                .totalSpent(total)
                                .build()));
    }
}

