package com.expenses.model.dTo.mapper;

import com.expenses.model.dTo.ExpenseDto;
import com.expenses.model.expense.Expense;

import java.util.*;
import java.util.stream.Collectors;

public class ExpenseMapper {
    public static ExpenseDto toDto(Expense expense) {
        return ExpenseDto.builder()
                .id(expense.getId())
                .name(expense.getName())
                .description(expense.getDescription())
                .price(expense.getPrice())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .category(expense.getCategory())
                .userId(expense.getUserId())
                .build();
    }

    public static Expense toEntity(ExpenseDto expenseDto) {
        return Expense.builder()
                .id(expenseDto.getId())
                .name(expenseDto.getName())
                .description(expenseDto.getDescription())
                .price(expenseDto.getPrice())
                .amount(expenseDto.getAmount())
                .date(expenseDto.getDate())
                .category(expenseDto.getCategory())
                .userId(expenseDto.getUserId())
                .build();
    }
    public static List<ExpenseDto> toDtoList(List<Expense> expenses) {
        return expenses.stream().map(ExpenseMapper::toDto).collect(Collectors.toList());
    }
    public static List<Expense> toEntityList(List<ExpenseDto> expenseDtos) {
        return expenseDtos.stream().map(ExpenseMapper::toEntity).collect(Collectors.toList());
    }
}
