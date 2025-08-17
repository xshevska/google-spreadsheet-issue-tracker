package com.xshevska.issuetracker.port.out;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;

import java.util.List;

public interface SpreadsheetFacade {
    Issue save(Issue issue);

    Issue updateStatus(String id, Status status);

    List<Issue> findByStatus(Status status);

    int maxNumericId(String prefix);
}
