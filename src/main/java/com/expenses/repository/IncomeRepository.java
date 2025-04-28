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
    Optional<List<Income>> findAllByDateBetweenAndUserId(String startDate, String endDate, String userId);
    Optional<List<Income>> findAllByDateAfterAndUserId(String startDate, String userId);

    Optional<List<Income>> findAllByDateBeforeAndUserId(String endDate, String userId);

}
