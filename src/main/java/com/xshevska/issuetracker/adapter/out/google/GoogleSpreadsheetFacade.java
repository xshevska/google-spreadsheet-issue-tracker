package com.xshevska.issuetracker.adapter.out.google;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.port.out.SpreadsheetFacade;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Profile("google")
public class GoogleSpreadsheetFacade implements SpreadsheetFacade {

    @Override
    public Issue save(Issue issue) {
        return null;
    }


    @Override
    public Issue updateStatus(String id, Status status) {
        return null;
    }

    @Override
    public List<Issue> findByStatus(Status status) {
        return List.of();
    }
}
