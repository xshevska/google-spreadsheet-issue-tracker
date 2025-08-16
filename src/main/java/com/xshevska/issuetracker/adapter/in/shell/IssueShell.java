package com.xshevska.issuetracker.adapter.in.shell;


import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.domain.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class IssueShell {

    private final IssueService issueService;

    @ShellMethod(key = "create", value = "Create a new issue")
    public Issue create(
            @ShellOption(help = "Description") String description,
            @ShellOption(defaultValue = ShellOption.NULL, help = "Parent ID") String parentId
    ) {
        return issueService.create(description,parentId);
    }

    @ShellMethod(key = "update", value= "Update the status of an existing issue")
    public Issue update(
            @ShellOption(help = "Issue ID") String id,
            @ShellOption(help = "New status (OPEN, IN_PROGRESS, CLOSED)") Status status
    ){
        return issueService.updateStatus(id, status);
    }

    @ShellMethod(key = "list", value= "List issues by status")
    public List<Issue> list(
            @ShellOption(help = "Status") Status status
    ){
        return issueService.listByStatus(status);
    }

}
