package com.xshevska.issuetracker.functional;

import com.xshevska.issuetracker.adapter.in.shell.IssueShell;
import com.xshevska.issuetracker.domain.exception.IssueNotFoundException;
import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.port.out.SpreadsheetFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "spring.main.banner-mode=off",
                "spring.shell.interactive.enabled=false",
                "logging.level.org.jline=OFF"
        }
)
@ActiveProfiles("test")
class IssueShellFunctionalTest {

    @Autowired
    IssueShell shell;
    @Autowired
    SpreadsheetFacade facade;

    @TestConfiguration
    static class MockConfig {
        @Bean
        SpreadsheetFacade spreadsheetFacade() {
            return mock(SpreadsheetFacade.class);
        }
    }

    @BeforeEach
    void resetMocks() {
        reset(facade);
    }

    private static OffsetDateTime ts(String iso) {
        return OffsetDateTime.parse(iso);
    }

    private static Issue issue(String id, String desc, Status st, String created, String updated) {
        return new Issue(id, desc, null, st,
                created == null ? null : ts(created),
                updated == null ? null : ts(updated));
    }

    private String list(String rawStatus) {
        return shell.list(rawStatus);
    }

    @Test
    void create_shouldGoThroughShellAndService() {
        when(facade.maxNumericId("AD-")).thenReturn(0);
        Issue saved = issue("AD-1", "desc", Status.OPEN, "2025-08-17T10:15:00Z", null);
        when(facade.save(any())).thenReturn(saved);

        Issue out = shell.create("desc", null);

        assertThat(out).isEqualTo(saved);
        verify(facade).maxNumericId("AD-");
        verify(facade).save(any(Issue.class));
    }

    @Test
    void updateStatus_shouldUseFacadeAndReturnUpdatedIssue() {
        Issue updated = issue("AD-1", "desc", Status.CLOSED,
                "2025-08-17T10:15:00Z", "2025-08-17T11:00:00Z");
        when(facade.updateStatus("AD-1", Status.CLOSED)).thenReturn(updated);

        Issue out = shell.updateStatus("AD-1", Status.CLOSED);

        assertThat(out.status()).isEqualTo(Status.CLOSED);
        verify(facade).updateStatus("AD-1", Status.CLOSED);
    }

    @Test
    void list_shouldReturnFriendlyMessageWhenEmpty() {
        when(facade.findByStatus(Status.OPEN)).thenReturn(List.of());

        String out = list("OPEN");

        assertThat(out).isEqualTo("No issues with status OPEN");
        verify(facade).findByStatus(Status.OPEN);
    }

    @Test
    void list_shouldJoinMultipleIssues() {
        var i1 = issue("AD-1", "a", Status.OPEN, "2025-08-17T10:15:00Z", null);
        var i2 = issue("AD-2", "b", Status.OPEN, "2025-08-17T10:16:00Z", null);
        when(facade.findByStatus(Status.OPEN)).thenReturn(List.of(i1, i2));

        String out = list("OPEN");

        assertThat(out).contains("AD-1", "AD-2", "\n\n");
        verify(facade).findByStatus(Status.OPEN);
    }

    @Test
    void updateStatus_shouldPropagateNotFound() {
        when(facade.updateStatus("AD-404", Status.CLOSED))
                .thenThrow(new IssueNotFoundException("AD-404"));

        assertThatThrownBy(() -> shell.updateStatus("AD-404", Status.CLOSED))
                .isInstanceOf(IssueNotFoundException.class)
                .hasMessageContaining("AD-404");
    }

    @Test
    void list_shouldNormalizeHumanStatus() {
        when(facade.findByStatus(Status.IN_PROGRESS)).thenReturn(List.of());

        String out = list("in-progress");

        assertThat(out).isEqualTo("No issues with status IN_PROGRESS");
        verify(facade).findByStatus(Status.IN_PROGRESS);
    }

    @Test
    void list_shouldHandleClosedIssues() {
        var i1 = issue("AD-3", "fix bug", Status.CLOSED, "2025-08-17T12:00:00Z", "2025-08-17T13:00:00Z");
        var i2 = issue("AD-4", "refactor", Status.CLOSED, "2025-08-17T12:10:00Z", "2025-08-17T13:10:00Z");
        when(facade.findByStatus(Status.CLOSED)).thenReturn(List.of(i1, i2));

        String out = list("CLOSED");

        assertThat(out).contains("AD-3", "AD-4", "\n\n");
        verify(facade).findByStatus(Status.CLOSED);
    }
}
