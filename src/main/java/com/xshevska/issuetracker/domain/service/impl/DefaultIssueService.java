package com.xshevska.issuetracker.domain.service.impl;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.domain.service.IssueService;
import com.xshevska.issuetracker.port.out.SpreadsheetFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultIssueService implements IssueService {

    private final SpreadsheetFacade spreadsheetFacade;

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
