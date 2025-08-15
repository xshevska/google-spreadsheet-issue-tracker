package com.xshevska.google_spreadsheet_issue_tracker.repository;

import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Issue;
import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Status;

import java.util.List;

public interface IssueRepository {
    Issue save(Issue issue);
    Issue updateStatus(String id, Status status);
    List<Issue> findByStatus(Status status);
}
