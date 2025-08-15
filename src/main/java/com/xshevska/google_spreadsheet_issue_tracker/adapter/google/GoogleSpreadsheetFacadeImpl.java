package com.xshevska.google_spreadsheet_issue_tracker.adapter.google;

import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Issue;
import com.xshevska.google_spreadsheet_issue_tracker.domain.model.Status;
import com.xshevska.google_spreadsheet_issue_tracker.port.SpreadsheetFacade;
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
