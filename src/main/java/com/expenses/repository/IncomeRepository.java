package com.expenses.repository;

import com.expenses.model.expense.Expense;
import com.expenses.model.incomes.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends MongoRepository<Income, String> {
    Optional<Income> findIncomeById(String id);
    Optional<List<Income>> findAllByUserId(String userId);
}
