package com.xshevska.issuetracker.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(GoogleSheetsProps.class)
@Profile("google")
public class GoogleSheetsConfig {

    private static final String APP_NAME = "google-spreadsheet-issue-tracker";

    @Bean
    Sheets sheets(GoogleSheetsProps props) throws Exception {
        try (InputStream in = resolveCredentials(props.credentialsPath())) {
            var creds = ServiceAccountCredentials.fromStream(in)
                    .createScoped(List.of(SheetsScopes.SPREADSHEETS));

            var http = GoogleNetHttpTransport.newTrustedTransport();

            return new Sheets.Builder(http, GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(creds))
                    .setApplicationName(APP_NAME)
                    .build();
        }
    }

    private InputStream resolveCredentials(String path) throws Exception {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("gsheets.credentials-path is empty");
        }

        Path filePath = Path.of(path);
        if (Files.exists(filePath)) {
            return Files.newInputStream(filePath);
        }

        String cp = path.startsWith("classpath:") ? path.substring("classpath:".length()) : path;
        InputStream in = GoogleSheetsConfig.class.getResourceAsStream(cp.startsWith("/") ? cp : "/" + cp);

        return Objects.requireNonNull(in, "Credentials not found: " + path);
    }
}