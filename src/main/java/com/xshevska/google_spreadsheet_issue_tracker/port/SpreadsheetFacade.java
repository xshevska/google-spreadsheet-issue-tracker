package com.xshevska.google_spreadsheet_issue_tracker.port;

import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Issue;
import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Status;

import java.util.List;

public interface SpreadsheetFacade {
    Issue createIssue(Issue issue);
    Issue updateIssueStatus(String id, Status status);
    List<Issue> listIssuesByStatus(Status status);
}
