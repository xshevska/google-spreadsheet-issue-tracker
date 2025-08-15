package com.xshevska.issuetracker.port;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;

import java.util.List;

public interface SpreadsheetFacade {
    Issue createIssue(Issue issue);
    Issue updateIssueStatus(String id, Status status);
    List<Issue> listIssuesByStatus(Status status);
}
