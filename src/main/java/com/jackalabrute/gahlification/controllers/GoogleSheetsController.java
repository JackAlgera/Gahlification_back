package com.jackalabrute.gahlification.controllers;

import com.jackalabrute.gahlification.exceptions.statuscodes.IncorrectRequestException;
import com.jackalabrute.gahlification.models.AddCategoryEntryResponse;
import com.jackalabrute.gahlification.models.BudgetCategory;
import com.jackalabrute.gahlification.models.CellPosition;
import com.jackalabrute.gahlification.models.CellRange;
import com.jackalabrute.gahlification.models.SetCategoryValueRequestBody;
import com.jackalabrute.gahlification.services.GoogleSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GoogleSheetsController {

    @Autowired
    private GoogleSheetsService googleSheetsService;

    @GetMapping(path = "/sheets/{sheetName}")
    public ResponseEntity<?> getSheetValue(@PathVariable String sheetName, @RequestParam String topLeftCellPosition, @RequestParam(required = false) String botRightCellPosition) throws IOException {
        CellPosition topLeftCell = new CellPosition(topLeftCellPosition);
        CellPosition botRightCell = botRightCellPosition != null ? new CellPosition(botRightCellPosition) : null;

        return ResponseEntity.ok(googleSheetsService.getCellRangeData(new CellRange(sheetName, topLeftCell, botRightCell)));
    }

    @GetMapping(path = "/sheets")
    public ResponseEntity<List<String>> getAllSheetNames() throws IOException {
        return ResponseEntity.ok(googleSheetsService.getAllSheetNames());
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<List<BudgetCategory>> getAllCategories() {
        return ResponseEntity.ok(List.of(BudgetCategory.values()));
    }

    @PostMapping(path = "/sheets/{sheetName}/categories/{category}")
    public ResponseEntity<AddCategoryEntryResponse> setCategoryValue(
            @PathVariable String sheetName,
            @PathVariable String category,
            @RequestBody SetCategoryValueRequestBody requestBody) throws IOException {
        BudgetCategory budgetCategory = BudgetCategory.getValue(category);

        if (budgetCategory == null) {
            throw new IncorrectRequestException(String.format("Category %s doesn't exist.", category));
        }

        CellRange insertPosition = googleSheetsService.getFirstEmptyCellForCategory(sheetName, budgetCategory);

        googleSheetsService.writeCell(insertPosition, "" + requestBody.getCost());
        googleSheetsService.writeCell(insertPosition.getShiftedCellRange(-2), requestBody.getDescription());
        return ResponseEntity.ok(new AddCategoryEntryResponse(insertPosition.toStringValue()));
    }

    @PostMapping(path = "/sheets/{sheetName}")
    public ResponseEntity<?> setSheetValue(@PathVariable String sheetName, @RequestParam String cellPosition, @RequestParam String data) throws IOException {
        CellPosition cell = new CellPosition(cellPosition);
        CellRange cellRange = new CellRange(sheetName, cell, null);

        googleSheetsService.writeCell(cellRange, data);
        return ResponseEntity.ok(cellRange.toStringValue());
    }
}
