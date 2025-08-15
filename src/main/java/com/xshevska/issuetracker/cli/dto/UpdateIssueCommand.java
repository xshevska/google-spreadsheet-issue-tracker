package com.xshevska.issuetracker.cli.dto;

import com.xshevska.issuetracker.domain.model.Status;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class UpdateIssueCommand {
    private final String id;
    private final Status status;
}
