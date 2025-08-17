package com.xshevska.issuetracker.adapter.out.google;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.xshevska.issuetracker.config.GoogleSheetsProps;
import com.xshevska.issuetracker.domain.exception.IssueNotFoundException;
import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.port.out.SpreadsheetFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.google.api.services.sheets.v4.model.ValueRange;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("google")
@RequiredArgsConstructor
public class GoogleSpreadsheetFacade implements SpreadsheetFacade {

    private final Sheets sheets;
    private final GoogleSheetsProps props;

    private static final DateTimeFormatter TS_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private static final int COL_ID = 0;
    private static final int COL_DESC = 1;
    private static final int COL_PARENT_ID = 2;
    private static final int COL_STATUS = 3;
    private static final int COL_CREATED = 4;
    private static final int COL_UPDATED = 5;

    private String rangeAll() {
        return props.sheetName() + "!A:F";
    }

    private String rangeIds() {
        return props.sheetName() + "!A:A";
    }

    @Override
    public Issue save(Issue i) {
        var row = List.of(
                i.id(),
                nz(i.description()),
                nz(i.parentId()),
                i.status().name(),
                fmt(i.createdAt()),
                (Object) fmt(i.updatedAt())
        );
        try {
            var body = new ValueRange().setValues(List.of(row));
            sheets.spreadsheets().values()
                    .append(props.spreadsheetId(), rangeAll(), body)
                    .setValueInputOption("RAW")
                    .execute();
            return i;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save Issue to Google Sheets. Please check configuration and access permissions.", e);
        }
    }

    @Override
    public Issue updateStatus(String id, Status status) {
        try {
            var values = readAll();
            for (int r = 1; r < values.size(); r++) {
                var row = values.get(r);
                if (id.equals(val(row, COL_ID))) {
                    var updatedAt = OffsetDateTime.now().withSecond(0).withNano(0);
                    var updatedAtText = fmt(updatedAt);
                    var rowIndex = r + 1;
                    var rangeStatus = props.sheetName() + "!D" + rowIndex;
                    var rangeUpdated = props.sheetName() + "!F" + rowIndex;

                    var batch = new BatchUpdateValuesRequest()
                            .setValueInputOption("RAW")
                            .setData(List.of(
                                    new ValueRange().setRange(rangeStatus).setValues(List.of(List.of(status.name()))),
                                    new ValueRange().setRange(rangeUpdated).setValues(List.of(List.of(updatedAtText)))
                            ));

                    sheets.spreadsheets().values()
                            .batchUpdate(props.spreadsheetId(), batch)
                            .execute();
                    return new Issue(
                            id,
                            emptyToNull(val(row, COL_DESC)),
                            emptyToNull(val(row, COL_PARENT_ID)),
                            status,
                            parseTime(val(row, COL_CREATED)),
                            parseTime(updatedAtText)
                    );
                }
            }
            throw new IssueNotFoundException(id);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to update issue in Google Sheets. Check configuration and access.", e);
        }
    }

    @Override
    public List<Issue> findByStatus(Status status) {
        try {
            var values = readAll();
            List<Issue> out = new ArrayList<>();
            for (int r = 1; r < values.size(); r++) {
                var row = values.get(r);
                if (status.name().equals(val(row, COL_STATUS))) {
                    out.add(mapRow(row));
                }
            }
            return out;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read issues from Google Sheets. Check configuration and access.", e);
        }
    }

    @Override
    public int maxNumericId(String prefix) {
        try {
            int max = 0;
            for (var row : readIds()) {
                String id = row.isEmpty() ? "" : String.valueOf(row.get(0));
                if (id.startsWith(prefix)) {
                    String suffix = id.substring(prefix.length());
                    if (suffix.matches("\\d+")) {
                        max = Math.max(max, Integer.parseInt(suffix));
                    }
                }
            }
            return max;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to scan IDs in Google Sheets. Check configuration and access.", e);
        }
    }

    private List<List<Object>> readAll() throws IOException {
        var resp = sheets.spreadsheets().values()
                .get(props.spreadsheetId(), rangeAll())
                .execute();
        return resp.getValues() != null ? resp.getValues() : List.of();
    }

    private List<List<Object>> readIds() throws IOException {
        var resp = sheets.spreadsheets().values()
                .get(props.spreadsheetId(), rangeIds())
                .execute();
        return resp.getValues() != null ? resp.getValues() : List.of();
    }

    private Issue mapRow(List<Object> row) {
        return new Issue(
                val(row, COL_ID),
                emptyToNull(val(row, COL_DESC)),
                emptyToNull(val(row, COL_PARENT_ID)),
                Status.valueOf(val(row, COL_STATUS)),
                parseTime(emptyToNull(val(row, COL_CREATED))),
                parseTime(emptyToNull(val(row, COL_UPDATED)))
        );
    }

    private static String val(List<Object> row, int idx) {
        return idx < row.size() ? String.valueOf(row.get(idx)) : "";
    }

    private static String nz(String s) {
        return s == null ? "" : s;
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }

    private static String fmt(OffsetDateTime t) {
        if (t == null) return "";
        return t.withSecond(0).withNano(0).toLocalDateTime().format(TS_FMT);
    }

    private static OffsetDateTime parseTime(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            LocalDateTime ldt = LocalDateTime.parse(s, TS_FMT);
            return ldt.atOffset(ZoneOffset.UTC);
        } catch (Exception ignore) {
        }
        return OffsetDateTime.parse(s);
    }
}