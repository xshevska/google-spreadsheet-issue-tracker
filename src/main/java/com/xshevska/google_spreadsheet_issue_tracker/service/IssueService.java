package com.xshevska.google_spreadsheet_issue_tracker.service;

import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Issue;
import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Status;

import java.util.List;

public interface IssueService {
    Issue create(String description, String parentId);
    Issue updateStatus(String id, Status status);
    List<Issue> listByStatus(Status status);
}
