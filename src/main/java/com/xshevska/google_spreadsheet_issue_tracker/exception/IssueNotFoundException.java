package com.xshevska.google_spreadsheet_issue_tracker.exception;

public class IssueNotFoundException extends RuntimeException {
    public IssueNotFoundException(String id) {
        super("Issue not found: " + id);
    }
}
