package com.jackalabrute.gahlification.services;

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
import com.jackalabrute.gahlification.models.BudgetCategory;
import com.jackalabrute.gahlification.models.sheets.CellPosition;
import com.jackalabrute.gahlification.models.sheets.CellRange;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleSheetsService {
    private static final String APPLICATION_NAME = "Gahlificapption";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/google-credentials.json";
    private static final String SPREADSHEET_ID = "1r9w3Kn_pnoTIrX_EHQThV2XJ-MO_C5Q8pdEAYYfa8N0";

    private static final Integer CELL_POSITION_MARGIN = 7;

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

    public CellRange getFirstEmptyCellForCategory(String sheetName, BudgetCategory category) throws IOException {
        CellPosition cellPositionWithMargin = new CellPosition(
                category.startCellPosition.getColumn(),
                category.startCellPosition.getRow() + CELL_POSITION_MARGIN
        );

        List<List<Object>> data = getCellRangeData(new CellRange(sheetName, category.startCellPosition, cellPositionWithMargin));

        int nbrOfEntrees = data == null ? 0 : (int) data.stream().mapToLong(List::size).sum();
        return new CellRange(sheetName, new CellPosition(category.startCellPosition.getColumn(), category.startCellPosition.getRow() + nbrOfEntrees), null);
    }
}
