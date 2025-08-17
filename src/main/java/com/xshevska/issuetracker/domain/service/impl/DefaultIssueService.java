package com.xshevska.issuetracker.domain.service.impl;


import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.domain.service.IssueService;
import com.xshevska.issuetracker.port.out.SpreadsheetFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DefaultIssueService implements IssueService {

    private static final String ID_PREFIX = "AD-";

    private final SpreadsheetFacade spreadsheetFacade;

    @Override
    public Issue create(String description, String parentId) {
        int next = spreadsheetFacade.maxNumericId(ID_PREFIX) + 1;
        String id = ID_PREFIX + next;

        OffsetDateTime now = OffsetDateTime.now()
                .withSecond(0)
                .withNano(0);

        Issue issue = new Issue(
                id,
                description,
                parentId,
                Status.OPEN,
                now,
                now
        );
        return spreadsheetFacade.save(issue);
    }

    @Override
    public Issue updateStatus(String id, Status status) {
        return spreadsheetFacade.updateStatus(id, status);
    }

    @Override
    public List<Issue> listByStatus(Status status) {
        return spreadsheetFacade.findByStatus(status);
    }
}
