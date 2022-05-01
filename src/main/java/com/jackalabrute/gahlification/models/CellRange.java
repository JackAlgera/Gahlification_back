package com.jackalabrute.gahlification.models;

import lombok.Data;

@Data
public class CellRange {
    private String sheetName;
    private CellPosition topLeftCell, botRightCell;

    public CellRange(String sheetName, CellPosition topLeftCell, CellPosition botRightCell) {
        this.sheetName = sheetName;
        this.topLeftCell = topLeftCell;
        this.botRightCell = botRightCell;
    }

    public String toStringValue() {
        return sheetName + "!" + topLeftCell.toStringValue() + (botRightCell != null ? ":" + botRightCell.toStringValue(): "");
    }

    public CellRange getShiftedCellRange(Integer delta) {
        return new CellRange(
                this.sheetName,
                new CellPosition(this.topLeftCell.getColumn() + delta, this.topLeftCell.getRow()),
                null
        );
    }
}
