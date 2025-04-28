package com.expenses.service;

import com.expenses.model.expense.Expense;
import com.expenses.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> findAll(){
        return expenseRepository.findAll();
    }

    public Expense findById(String id){
        return expenseRepository.findExpenseById(id).orElseThrow();
    }

    public List<Expense> findAllByUserId(String userId){
        return expenseRepository.findAllByUserId(userId).orElseThrow();
    }

    public Expense save(Expense expense){
        return expenseRepository.save(expense);
    }

    public Expense update(Expense expense){
        Expense existedExpense = expenseRepository.findExpenseById(expense.getId()).orElseThrow();

        existedExpense.setName(expense.getName());
        existedExpense.setDescription(expense.getDescription());
        existedExpense.setPrice(expense.getPrice());
        existedExpense.setAmount(expense.getAmount());
        existedExpense.setDate(expense.getDate());
        existedExpense.setCategory(expense.getCategory());

        existedExpense.setUserId(expense.getUserId());

        return expenseRepository.save(existedExpense);
    }

    public Expense delete(String id){
        Expense existedExpense = expenseRepository.findExpenseById(id).orElseThrow();
        expenseRepository.delete(existedExpense);
        return existedExpense;
    }

    public List<Expense> findAllByDateBetween(String startDate, String endDate){
        return expenseRepository.findAllByDateBetween(startDate, endDate).orElse(null);
    }

    public List<Expense>findAllByDateBetweenAndUserId(String startDate, String endDate, String userId){
        return expenseRepository.findAllByDateBetweenAndUserId(startDate, endDate, userId).orElse(null);
    }

    public List<Expense> findAllByDateAfterAndUserId(String startDate, String userId){
        return expenseRepository.findAllByDateAfterAndUserId(startDate, userId).orElse(null);
    }

    public List<Expense> findAllByDateBeforeAndUserId(String endDate, String userId){
        return expenseRepository.findAllByDateBeforeAndUserId(endDate, userId).orElse(null);
    }
}
