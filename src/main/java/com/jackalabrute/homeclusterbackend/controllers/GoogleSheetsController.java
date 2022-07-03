package com.jackalabrute.homeclusterbackend.controllers;

import com.jackalabrute.homeclusterbackend.exceptions.statuscodes.IncorrectRequestException;
import com.jackalabrute.homeclusterbackend.models.sheets.Budget;
import com.jackalabrute.homeclusterbackend.models.sheets.BudgetCategory;
import com.jackalabrute.homeclusterbackend.models.sheets.BudgetCategoryType;
import com.jackalabrute.homeclusterbackend.models.sheets.BudgetEntry;
import com.jackalabrute.homeclusterbackend.models.sheets.CellPosition;
import com.jackalabrute.homeclusterbackend.models.sheets.CellRange;
import com.jackalabrute.homeclusterbackend.models.web.AddCategoryEntryResponse;
import com.jackalabrute.homeclusterbackend.services.GoogleSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GoogleSheetsController {

    @Autowired
    private GoogleSheetsService googleSheetsService;

    @GetMapping(path = "/sheets/names")
    public ResponseEntity<List<String>> getAllSheetNames() throws IOException {
        return ResponseEntity.ok(googleSheetsService.getAllSheetNames());
    }

    @GetMapping(path = "/categories/types")
    public ResponseEntity<List<String>> getAllCategoryTypes() {
        return ResponseEntity.ok(
                Arrays.stream(BudgetCategoryType.values())
                        .map(category -> category.value)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(path = "/sheets/{sheetName}/budget")
    public ResponseEntity<Budget> getGlobalBudget(@PathVariable String sheetName) throws IOException {
        return ResponseEntity.ok(googleSheetsService.getGlobalBudget(sheetName));
    }

    @GetMapping(path = "/sheets/{sheetName}/categories/{category}/budget")
    public ResponseEntity<BudgetCategory> getCategoryBudget(@PathVariable String sheetName, @PathVariable String category) throws IOException {
        BudgetCategoryType budgetCategoryType = BudgetCategoryType.getValue(category);

        if (budgetCategoryType == null) {
            throw new IncorrectRequestException(String.format("Category %s doesn't exist.", category));
        }

        return ResponseEntity.ok(googleSheetsService.getBudgetCategory(sheetName, budgetCategoryType));
    }

    @PostMapping(path = "/sheets/{sheetName}/categories/{category}")
    public ResponseEntity<AddCategoryEntryResponse> addCategoryEntry(
            @PathVariable String sheetName,
            @PathVariable String category,
            @RequestBody BudgetEntry requestBody) throws IOException {
        BudgetCategoryType budgetCategoryType = BudgetCategoryType.getValue(category);

        if (budgetCategoryType == null) {
            throw new IncorrectRequestException(String.format("Category %s doesn't exist.", category));
        }

        CellRange insertPosition = googleSheetsService.getFirstEmptyCellForCategory(sheetName, budgetCategoryType);

        googleSheetsService.writeCell(insertPosition, "" + requestBody.getCost());
        googleSheetsService.writeCell(insertPosition.getShiftedCellRange(-2), requestBody.getDescription());
        return ResponseEntity.ok(new AddCategoryEntryResponse(insertPosition.toStringValue()));
    }

    // Endpoints for testing

    @GetMapping(path = "/sheets/{sheetName}")
    public ResponseEntity<?> getSheetValue(@PathVariable String sheetName, @RequestParam String topLeftCellPosition, @RequestParam(required = false) String botRightCellPosition) throws IOException {
        CellPosition topLeftCell = new CellPosition(topLeftCellPosition);
        CellPosition botRightCell = botRightCellPosition != null ? new CellPosition(botRightCellPosition) : null;

        return ResponseEntity.ok(googleSheetsService.getCellRangeData(new CellRange(sheetName, topLeftCell, botRightCell)));
    }

    @PostMapping(path = "/sheets/{sheetName}")
    public ResponseEntity<?> setSheetValue(@PathVariable String sheetName, @RequestParam String cellPosition, @RequestParam String data) throws IOException {
        CellPosition cell = new CellPosition(cellPosition);
        CellRange cellRange = new CellRange(sheetName, cell, null);

        googleSheetsService.writeCell(cellRange, data);
        return ResponseEntity.ok(cellRange.toStringValue());
    }
}
