package com.vikas.financetracker.repository;

import com.example.reactivefinancetracker.domain.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    Flux<Transaction> findAllByUserId(String userId);

}

