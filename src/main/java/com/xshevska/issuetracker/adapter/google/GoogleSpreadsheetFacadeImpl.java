package com.xshevska.issuetracker.adapter.google;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.port.SpreadsheetFacade;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("google")
public class GoogleSpreadsheetFacadeImpl implements SpreadsheetFacade {

    @Override
    public Issue createIssue(Issue issue) {
        return null;
    }

    @Override
    public Issue updateIssueStatus(String id, Status status) {
        return null;
    }

    @Override
    public List<Issue> listIssuesByStatus(Status status) {
        return List.of();
    }
}
