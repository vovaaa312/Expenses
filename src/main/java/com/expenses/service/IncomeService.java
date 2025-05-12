package com.expenses.service;

import com.expenses.model.dTo.IncomeDto;
import com.expenses.model.dTo.mapper.IncomeMapper;
import com.expenses.model.incomes.Income;
import com.expenses.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public Income findById(String id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income not found with id: " + id));
    }

    public List<Income> findAllByUserId(String userId) {
        return incomeRepository.findAllByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("No incomes found for user: " + userId));
    }



    public Income save(Income income) {
        return incomeRepository.save(income);
    }

    public Income update(Income income) {
        incomeRepository.findById(income.getId())
                .orElseThrow(() -> new EntityNotFoundException("Income not found with id: " + income.getId()));
        return incomeRepository.save(income);
    }

    public Income delete(String id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income not found with id: " + id));
        incomeRepository.delete(income);
        return income;
    }

    public List<Income> findAllByDateBetweenAndUserId(String startDate, String endDate, String userId) {
        return incomeRepository.findAllByDateBetweenAndUserId(startDate, endDate, userId)
                .orElseThrow(() -> new EntityNotFoundException("No incomes found for user in the specified date range"));
    }

    public List<Income> findAllByDateAfterAndUserId(String startDate, String userId) {
        return incomeRepository.findAllByDateAfterAndUserId(startDate, userId)
                .orElseThrow(() -> new EntityNotFoundException("No incomes found for user after the specified date"));
    }

    public List<Income> findAllByDateBeforeAndUserId(String endDate, String userId) {
        return incomeRepository.findAllByDateBeforeAndUserId(endDate, userId)
                .orElseThrow(() -> new EntityNotFoundException("No incomes found for user before the specified date"));
    }
}