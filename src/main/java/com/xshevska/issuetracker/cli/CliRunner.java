package com.xshevska.issuetracker.cli;

import com.xshevska.issuetracker.service.IssueService;
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
