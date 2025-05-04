package com.expenses.model.incomes;

import com.expenses.model.dTo.IncomeDto;
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

    public IncomeDto toDto(){
        return IncomeDto.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .amount(this.amount)
                .date(this.date)
                .category(this.category)
                .userId(this.userId)
                .build();
    }
    public static Income fromDto(IncomeDto incomeDto) {
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

}
