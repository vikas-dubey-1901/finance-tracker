package com.vikas.financetracker.repository;

import com.example.reactivefinancetracker.domain.Alert;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AlertRepository extends ReactiveMongoRepository<Alert, String> {
    Flux<Alert> findAllByUserIdOrderByCreatedAtDesc(String userId);
}

