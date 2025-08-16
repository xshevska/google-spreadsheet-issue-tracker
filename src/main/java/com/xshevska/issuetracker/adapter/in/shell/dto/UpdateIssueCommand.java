package com.xshevska.issuetracker.adapter.in.shell.dto;

import com.xshevska.issuetracker.domain.model.Status;

public record UpdateIssueCommand(String id, Status status) {}