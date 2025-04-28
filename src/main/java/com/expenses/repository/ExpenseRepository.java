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

    Optional<List<Expense>> findAllByDateBetween(String startDate, String endDate);

    Optional<List<Expense>> findAllByDateBetweenAndUserId(String startDate, String endDate, String userId);

    Optional<List<Expense>> findAllByDateAfterAndUserId(String startDate, String userId);

    Optional<List<Expense>> findAllByDateBeforeAndUserId(String endDate, String userId);

}
