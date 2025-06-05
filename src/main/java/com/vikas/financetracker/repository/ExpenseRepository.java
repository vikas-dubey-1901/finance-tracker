package com.vikas.financetracker.repository;

import com.vikas.financetracker.model.Expense;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends ReactiveMongoRepository<Expense, String> {
}
