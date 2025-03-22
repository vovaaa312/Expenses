package com.expenses.model.incomes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IncomeSource {
    SALARY("Salary"),
    BONUS("Bonus"),
    GIFT("Gift"),
    OTHER("Other");

    private final String source;
}
