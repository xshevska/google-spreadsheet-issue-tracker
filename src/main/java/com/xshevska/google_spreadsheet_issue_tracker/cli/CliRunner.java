package com.xshevska.google_spreadsheet_issue_tracker.cli;

import com.xshevska.google_spreadsheet_issue_tracker.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CliRunner implements CommandLineRunner {
    private final IssueService issueService;

    @Override
    public void run(String... args) throws Exception {

    }
}
