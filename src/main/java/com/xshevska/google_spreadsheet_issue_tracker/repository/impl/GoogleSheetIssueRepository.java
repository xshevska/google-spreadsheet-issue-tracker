package com.xshevska.google_spreadsheet_issue_tracker.repository.impl;

import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Issue;
import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Status;
import com.xshevska.google_spreadsheet_issue_tracker.repository.IssueRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoogleSheetIssueRepository implements IssueRepository {
    @Override
    public Issue save(Issue issue) {
        return null;
    }

    @Override
    public Issue updateStatus(String id, Status status) {
        return null;
    }

    @Override
    public List<Issue> findByStatus(Status status) {
        return List.of();
    }
}
