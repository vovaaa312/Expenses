package com.expenses.model.dTo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Data

public class ExpenseDto {
    private String id;
    private String name;
    private String description;
    private double amount;
    private String date;
    private String category;
    private String userId;
    private double price;

}
