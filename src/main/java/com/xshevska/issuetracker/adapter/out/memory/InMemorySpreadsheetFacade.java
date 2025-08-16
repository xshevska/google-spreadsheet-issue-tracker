package com.xshevska.issuetracker.adapter.out.memory;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.port.out.SpreadsheetFacade;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.*;

@Component
@Profile("memory")
public class InMemorySpreadsheetFacade implements SpreadsheetFacade {

    private final Map<String, Issue> storage = new HashMap<>();

    @Override
    public Issue save(Issue issue) {
        storage.put(issue.getId(), issue);
        return issue;
    }

    @Override
    public Issue updateStatus(String id, Status status) {
        Issue issue = storage.get(id);
        if (issue == null) {
            throw new NoSuchElementException("Issue with id " + id + " not found");
        }

        Issue updated = new Issue(
                issue.getId(),
                issue.getDescription(),
                issue.getParentId(),
                status,
                issue.getCreatedAt(),
                OffsetDateTime.now()
        );
        storage.put(id, updated);
        return updated;
    }

    @Override
    public List<Issue> findByStatus(Status status) {
        return storage.values().stream()
                .filter(issue -> issue.getStatus() == status)
                .toList();
    }
}
