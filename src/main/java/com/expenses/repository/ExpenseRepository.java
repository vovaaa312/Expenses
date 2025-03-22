package com.expenses.repository;

import com.expenses.model.expense.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    Optional<Expense> findExpenseById(String id);
    Optional<List<Expense>> findAllByUserId(String userId);
}
