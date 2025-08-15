package com.xshevska.issuetracker.cli.dto;

import com.xshevska.issuetracker.domain.model.Status;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class ListIssuesQuery {
    private final Status status;
}
