package io.github.brewagebear;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class GoogleSheetService {

    public Sheets connect() {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory gsonFactory = GsonFactory.getDefaultInstance();

            HttpRequestInitializer httpRequestInitializer = new HttpCredentialsAdapter(getCredentialsFromStream());

            return new Sheets.Builder(httpTransport, gsonFactory, httpRequestInitializer)
                    .setApplicationName("Google Sheet Reader")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error creating Sheets service: ", e);
        }
    }

    private GoogleCredentials getCredentialsFromStream() {
        try (InputStream stream = new ByteArrayInputStream(Objects.requireNonNull(GoogleSheetService.class.getResourceAsStream("/credentials.json")).readAllBytes())) {
            return ServiceAccountCredentials.fromStream(stream)
                    .createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY));
        } catch (Exception e) {
            throw new RuntimeException("Error creating Google credentials: ", e);
        }
    }

    public List<List<String>> getResponse(String sheetIds, Sheets sheets, String range) throws IOException {
        log.info("Google Sheet API Params : {}, {}", sheetIds, range);

        ValueRange execute = sheets.spreadsheets().values()
                .get(sheetIds, range)
                .execute();

        return execute.getValues().stream()
                .map(row -> row.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
