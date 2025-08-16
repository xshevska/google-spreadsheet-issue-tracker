package com.xshevska.issuetracker.domain.service;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;

import java.util.List;

public interface IssueService {
    Issue create(String description, String parentId);
    Issue updateStatus(String id, Status status);
    List<Issue> listByStatus(Status status);
}
