package com.xshevska.issuetracker.repository.impl;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.repository.IssueRepository;
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
