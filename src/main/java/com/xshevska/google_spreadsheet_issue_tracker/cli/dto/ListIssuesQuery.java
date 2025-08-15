package com.xshevska.google_spreadsheet_issue_tracker.cli.dto;

import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Status;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class ListIssuesQuery {
    private final Status status;
}
