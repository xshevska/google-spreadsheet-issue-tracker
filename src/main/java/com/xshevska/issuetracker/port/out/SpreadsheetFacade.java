package com.xshevska.issuetracker.port.out;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;

import java.util.List;
import java.util.Optional;

public interface SpreadsheetFacade {
    Issue save(Issue issue);
    Optional<Issue> findById(String id);
    Issue updateStatus(String id, Status status);
    List<Issue> findByStatus(Status status);
}
