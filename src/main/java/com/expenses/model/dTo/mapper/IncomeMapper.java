package com.expenses.model.dTo.mapper;

import com.expenses.model.dTo.IncomeDto;
import com.expenses.model.incomes.Income;

import java.util.*;

public class IncomeMapper {

    public static IncomeDto toDto(Income income) {
        return IncomeDto.builder()
                .id(income.getId())
                .name(income.getName())
                .description(income.getDescription())
                .amount(income.getAmount())
                .date(income.getDate())
                .category(income.getCategory())
                .userId(income.getUserId())
                .build();
    }

    public static Income toEntity(IncomeDto incomeDto) {
        return Income.builder()
                .id(incomeDto.getId())
                .name(incomeDto.getName())
                .description(incomeDto.getDescription())
                .amount(incomeDto.getAmount())
                .date(incomeDto.getDate())
                .category(incomeDto.getCategory())
                .userId(incomeDto.getUserId())
                .build();
    }

    public static List<IncomeDto> toDtoList(List<Income> incomes) {
        return incomes.stream().map(IncomeMapper::toDto).toList();
    }

    public static List<Income> toEntityList(List<IncomeDto> incomeDtos) {
        return incomeDtos.stream().map(IncomeMapper::toEntity).toList();
    }

}
