package com.xshevska.issuetracker.domain.exception;

public class IssueNotFoundException extends RuntimeException {
    public IssueNotFoundException(String id) {
        super("Issue not found: " + id);
    }
}
