package com.xshevska.issuetracker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gsheets")
public record GoogleSheetsProps(String spreadsheetId, String sheetName, String credentialsPath) {
}
