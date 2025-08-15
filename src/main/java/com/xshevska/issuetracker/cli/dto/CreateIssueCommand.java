package com.xshevska.issuetracker.cli.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class CreateIssueCommand {
    private final String description;
    private final String parentId;
}
