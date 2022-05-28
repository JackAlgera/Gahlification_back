package com.jackalabrute.gahlification.models.sheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetEntry implements Serializable {
    private String description;
    private Float cost;
}
