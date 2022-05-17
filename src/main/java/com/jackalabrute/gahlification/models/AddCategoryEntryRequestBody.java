package com.jackalabrute.gahlification.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryEntryRequestBody implements Serializable {
    private String description;
    private Float cost;
}
