package com.xshevska.google_spreadsheet_issue_tracker.cli.dto;

import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Status;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class UpdateIssueCommand {
    private final String id;
    private final Status status;
}
