package com.expenses.model.incomes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("Incomes")
public class Income {

    private String id;
    private String name;
    private String description;
    private double amount;
    private String date;
    private String category;

    private String userId;
}
