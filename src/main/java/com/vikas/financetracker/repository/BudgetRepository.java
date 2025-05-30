package com.vikas.financetracker.repository;

import com.example.reactivefinancetracker.domain.Budget;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BudgetRepository extends ReactiveMongoRepository<Budget, String> {
    Flux<Budget> findAllByUserIdAndYearAndMonth(String userId, int year, int month);
    Mono<Budget> findByUserIdAndCategoryAndYearAndMonth(String userId, String category, int year, int month);
}

