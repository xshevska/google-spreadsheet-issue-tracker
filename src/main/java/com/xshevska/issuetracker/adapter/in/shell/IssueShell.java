package com.xshevska.issuetracker.adapter.in.shell;


import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.domain.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class IssueShell {

    private final IssueService issueService;
    private static final String ALLOWED = "OPEN, IN_PROGRESS, CLOSED";

    @ShellMethod(key = {"issue create", "create"}, value = "Create a new issue")
    public Issue create(
            @ShellOption(help = "Description") String description,
            @ShellOption(defaultValue = ShellOption.NULL, help = "Parent ID") String parentId
    ) {
        return issueService.create(description, parentId);
    }

    @ShellMethod(
            key = {"issue update-status", "update-status"},
            value = "Update the status of an existing issue"
    )
    public Issue updateStatus(
            @ShellOption(help = "Issue ID") String id,
            @ShellOption(help = "New status (OPEN, IN_PROGRESS, CLOSED)") Status status
    ) {
        return issueService.updateStatus(id, status);
    }

    @ShellMethod(key = {"issue list", "list"}, value = "List issues by status")
    public String list(
            @ShellOption(help = "Status (" + ALLOWED + ")") String status
    ) {
        Status st = parseStatus(status);
        var issues = issueService.listByStatus(st);
        if (issues.isEmpty()) {
            return "No issues with status " + st;
        }
        return issues.stream()
                .map(Issue::toString)
                .collect(Collectors.joining("\n\n"));
    }

    private Status parseStatus(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Status is required. Allowed: " + ALLOWED);
        }
        String norm = raw.trim().toUpperCase()
                .replace('-', '_')
                .replace(' ', '_');
        try {
            return Status.valueOf(norm);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown status: " + raw + ". Allowed: " + ALLOWED);
        }
    }
}
