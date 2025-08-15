package com.xshevska.google_spreadsheet_issue_tracker.domain.model;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class Issue {
    private final String id;
    private final String description;
    private final String parentId;
    private final Status status;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
}
