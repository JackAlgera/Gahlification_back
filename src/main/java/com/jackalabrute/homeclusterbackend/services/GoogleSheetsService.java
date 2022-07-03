package com.jackalabrute.homeclusterbackend.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.jackalabrute.homeclusterbackend.exceptions.statuscodes.IncorrectRequestException;
import com.jackalabrute.homeclusterbackend.models.sheets.Budget;
import com.jackalabrute.homeclusterbackend.models.sheets.BudgetCategory;
import com.jackalabrute.homeclusterbackend.models.sheets.BudgetCategoryType;
import com.jackalabrute.homeclusterbackend.models.sheets.BudgetEntry;
import com.jackalabrute.homeclusterbackend.models.sheets.CellPosition;
import com.jackalabrute.homeclusterbackend.models.sheets.CellRange;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoogleSheetsService {
    private static final String APPLICATION_NAME = "Gahlificapption";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/google-credentials.json";
    private static final String SPREADSHEET_ID = "1r9w3Kn_pnoTIrX_EHQThV2XJ-MO_C5Q8pdEAYYfa8N0";

    private static final Integer MAX_ENTRIES_BUDGET = 8;
    private static final Integer EXPENSE_TO_BUDGET_GAP = (MAX_ENTRIES_BUDGET + 1) * 2 + 4;

    private static final CellPosition TOP_LEFT_SHEET_POSITION   = new CellPosition("B", 3);
    private static final CellPosition BOT_RIGHT_SHEET_POSITION  = new CellPosition("T", 21);

    private static Sheets service = null;

    @PostConstruct
    public void init() throws GeneralSecurityException, IOException {
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpCredentialsAdapter(getCredentials()))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private GoogleCredentials getCredentials() throws IOException {
        InputStream in = GoogleSheetsService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        return GoogleCredentials.fromStream(in).createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS));
    }

    public List<List<Object>> getCellRangeData(CellRange cellRange) throws IOException {
        ValueRange response = service.spreadsheets().values()
                                     .get(SPREADSHEET_ID, cellRange.toStringValue())
                                     .execute();
        return response.getValues();
    }

    public void writeCell(CellRange cellRange, String data) throws IOException {
        ValueRange body = new ValueRange().setRange(cellRange.toStringValue()).setValues(List.of(List.of(data)));
        service.spreadsheets().values()
                .update(SPREADSHEET_ID, cellRange.toStringValue(), body)
                .setValueInputOption("USER_ENTERED")
                .execute();
    }

    public List<String> getAllSheetNames() throws IOException {
        Spreadsheet spreadsheet = service.spreadsheets().get(SPREADSHEET_ID).execute();
        return spreadsheet.getSheets().stream().map(sheet -> sheet.getProperties().getTitle()).collect(Collectors.toList());
    }

    public CellRange getFirstEmptyCellForCategory(String sheetName, BudgetCategoryType category) throws IOException {
        CellPosition cellPositionWithMargin = new CellPosition(
                category.startCellCostPosition.getColumn(),
                category.startCellCostPosition.getRow() + MAX_ENTRIES_BUDGET - 1
        );

        List<List<Object>> data = getCellRangeData(new CellRange(sheetName, category.startCellCostPosition, cellPositionWithMargin));

        int nbrOfEntrees = data == null ? 0 : (int) data.stream().mapToLong(List::size).sum();
        return new CellRange(sheetName, new CellPosition(category.startCellCostPosition.getColumn(), category.startCellCostPosition.getRow() + nbrOfEntrees), null);
    }

    public Budget getGlobalBudget(String sheetName) throws IOException {
        // Check if sheetName exists

        // Get current values
        CellRange currentDepensesRange = new CellRange(sheetName, TOP_LEFT_SHEET_POSITION, BOT_RIGHT_SHEET_POSITION);

        Map<BudgetCategoryType, Object> typeToRawDataMap = new HashMap<>();
        List<List<Object>> currentDepensesRawData = getCellRangeData(currentDepensesRange);


        // Get budget

        return null;
    }

    public BudgetCategory getBudgetCategory(String sheetName, BudgetCategoryType type) throws IOException {
        CellRange currentCategoryRange = new CellRange(sheetName, type.startCellCostPosition.getShiftedCell(-2, -1), type.startCellCostPosition.getShiftedCell(0, EXPENSE_TO_BUDGET_GAP - 1));
        List<List<Object>> currentExpsenseRawData = getCellRangeData(currentCategoryRange);

        float totalSpent = getBudgetEntryFromRawData(currentExpsenseRawData.get(0)).getCost();
        float budget = getBudgetEntryFromRawData(currentExpsenseRawData.get(currentExpsenseRawData.size() - 1)).getCost();
        List<BudgetEntry> entries = currentExpsenseRawData.stream()
                                                          .skip(1)
                                                          .limit(MAX_ENTRIES_BUDGET + 1)
                                                          .filter(rawEntry -> !rawEntry.isEmpty())
                                                          .map(this::getBudgetEntryFromRawData)
                                                          .collect(Collectors.toList());

        return new BudgetCategory(type, entries, totalSpent, budget);
    }

    private BudgetEntry getBudgetEntryFromRawData(List<Object> rawData) {
        if (rawData.size() < 3) {
            throw new IncorrectRequestException("Internal error, something went wrong on GCP side, response size is too small.");
        }

        return new BudgetEntry((String) rawData.get(0), Float.parseFloat((String) rawData.get(2)));
    }
}
