package com.jackalabrute.homeclusterbackend.models.sheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget implements Serializable {
    private List<BudgetCategory> budgetCategories;
}
