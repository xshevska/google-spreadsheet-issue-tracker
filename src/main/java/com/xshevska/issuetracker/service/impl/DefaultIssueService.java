package com.xshevska.issuetracker.service.impl;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.service.IssueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultIssueService implements IssueService {

    @Override
    public Issue create(String description, String parentId) {
        return null;
    }

    @Override
    public Issue updateStatus(String id, Status status) {
        return null;
    }

    @Override
    public List<Issue> listByStatus(Status status) {
        return List.of();
    }
}
