package com.expenses.service;

import com.expenses.model.expense.Expense;
import com.expenses.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public Expense findById(String id) {
        return expenseRepository.findExpenseById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));
    }

    public List<Expense> findAllByUserId(String userId) {
        return expenseRepository.findAllByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("No expenses found for user: " + userId));
    }

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense update(Expense expense) {
        expenseRepository.findExpenseById(expense.getId())
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + expense.getId()));

        return expenseRepository.save(
                Expense.builder()
                        .id(expense.getId())
                        .name(expense.getName())
                        .description(expense.getDescription())
                        .price(expense.getPrice())
                        .amount(expense.getAmount())
                        .date(expense.getDate())
                        .category(expense.getCategory())
                        .userId(expense.getUserId())
                        .build()
        );
    }

    public Expense delete(String id) {
        Expense existedExpense = expenseRepository.findExpenseById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));
        expenseRepository.delete(existedExpense);
        return existedExpense;
    }

    public List<Expense> findAllByDateBetween(String startDate, String endDate) {
        return expenseRepository.findAllByDateBetween(startDate, endDate)
                .orElseThrow(() -> new EntityNotFoundException("No expenses found in date range"));
    }

    public List<Expense> findAllByDateBetweenAndUserId(String startDate, String endDate, String userId) {
        return expenseRepository.findAllByDateBetweenAndUserId(startDate, endDate, userId)
                .orElseThrow(() -> new EntityNotFoundException("No expenses found for user in date range"));
    }

    public List<Expense> findAllByDateAfterAndUserId(String startDate, String userId) {
        return expenseRepository.findAllByDateAfterAndUserId(startDate, userId)
                .orElseThrow(() -> new EntityNotFoundException("No expenses found for user after date"));
    }

    public List<Expense> findAllByDateBeforeAndUserId(String endDate, String userId) {
        return expenseRepository.findAllByDateBeforeAndUserId(endDate, userId)
                .orElseThrow(() -> new EntityNotFoundException("No expenses found for user before date"));
    }
}