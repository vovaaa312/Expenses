package com.expenses.service;

import com.expenses.model.incomes.Income;
import com.expenses.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncomesService {
    private final IncomeRepository incomeRepository;

    public List<Income> findAll() {
        return incomeRepository.findAll();
    }

    public Income findById(String id) {
        return incomeRepository.findIncomeById(id).orElseThrow();
    }

    public List<Income> findAllByUserId(String userId) {
        return incomeRepository.findAllByUserId(userId).orElseThrow();
    }

    public Income save(Income income) {
        return incomeRepository.save(income);
    }

    public Income update(Income income) {
        Income existedIncome = incomeRepository.findIncomeById(income.getId()).orElseThrow();

        existedIncome.setName(income.getName());
        existedIncome.setDescription(income.getDescription());
        existedIncome.setAmount(income.getAmount());
        existedIncome.setDate(income.getDate());
        existedIncome.setCategory(income.getCategory());
        existedIncome.setUserId(income.getUserId());


        return incomeRepository.save(existedIncome);
    }

    public Income delete(String id) {
        Income existedIncome = incomeRepository.findIncomeById(id).orElseThrow();
        incomeRepository.delete(existedIncome);
        return existedIncome;
    }

    public List<Income> findAllByDateBetweenAndUserId(String startDate, String endDate, String userId) {
        return incomeRepository.findAllByDateBetweenAndUserId(startDate, endDate, userId).orElse(null);
    }

    public List<Income> findAllByDateAfterAndUserId(String startDate, String userId) {
        return incomeRepository.findAllByDateAfterAndUserId(startDate, userId).orElse(null);
    }

    public List<Income> findAllByDateBeforeAndUserId(String endDate, String userId) {
        return incomeRepository.findAllByDateBeforeAndUserId(endDate, userId).orElse(null);
    }
}
