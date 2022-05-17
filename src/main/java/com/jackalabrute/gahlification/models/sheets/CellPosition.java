package com.jackalabrute.gahlification.models.sheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CellPosition {
    private static final List<String> COLUMNS = List.of(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    private Integer column;
    private Integer row;

    public CellPosition(String position) {
        String[] pos = position.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        this.column = COLUMNS.indexOf(pos[0]);
        this.row = Integer.parseInt(pos[1]);
    }

    public CellPosition(String column, Integer row) {
        this.column = COLUMNS.indexOf(column);
        this.row = row;
    }

    public String toStringValue() {
        return String.format("%s%s", COLUMNS.get(column), row);
    }
}
