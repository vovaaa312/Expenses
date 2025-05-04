package com.expenses.model.dTo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncomeDto {
    private String id;
    private String name;
    private String description;
    private double amount;
    private String date;
    private String category;
    private String userId;


}
