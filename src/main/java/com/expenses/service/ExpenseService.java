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

}
