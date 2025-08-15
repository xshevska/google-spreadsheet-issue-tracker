package com.xshevska.issuetracker.repository;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;

import java.util.List;

public interface IssueRepository {
    Issue save(Issue issue);
    Issue updateStatus(String id, Status status);
    List<Issue> findByStatus(Status status);
}
