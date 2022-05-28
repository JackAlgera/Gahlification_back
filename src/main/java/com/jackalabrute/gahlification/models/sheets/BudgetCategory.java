package com.jackalabrute.gahlification.models.sheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetCategory implements Serializable {
    private BudgetCategoryType type;
    private List<BudgetEntry> entries;
    private Float totalSpent;
    private Float budget;
}
