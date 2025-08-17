package com.xshevska.issuetracker.domain.model;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class IssueTest {

    @Test
    void toString_shouldFormatDatesProperly() {
        OffsetDateTime created = OffsetDateTime.parse("2025-08-17T10:15:00Z");
        Issue issue = new Issue("AD-1", "desc", null, Status.OPEN, created, null);
        String out = issue.toString();

        assertThat(out).contains("Created: 2025-08-17T10:15");
        assertThat(out).contains("Updated: null");
        assertThat(out).contains("AD-1");
        assertThat(out).contains("desc");
        assertThat(out).contains("OPEN");
    }

    @Test
    void toString_shouldHandleBothDates() {
        OffsetDateTime created = OffsetDateTime.parse("2025-08-17T10:15:00Z");
        OffsetDateTime updated = OffsetDateTime.parse("2025-08-18T12:30:00Z");
        Issue issue = new Issue("AD-2", "desc", "AD-1", Status.CLOSED, created, updated);
        String out = issue.toString();
        assertThat(out).contains("Updated: 2025-08-18T12:30");
    }

    @Test
    void toString_shouldHandleNullDates() {
        Issue issue = new Issue("AD-3", "no dates", null, Status.IN_PROGRESS, null, null);
        String out = issue.toString();
        assertThat(out).contains("Created: null");
        assertThat(out).contains("Updated: null");
    }
}

