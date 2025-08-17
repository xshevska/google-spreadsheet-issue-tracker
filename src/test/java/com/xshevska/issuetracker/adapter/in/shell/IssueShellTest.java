package com.xshevska.issuetracker.adapter.in.shell;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.domain.service.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


class IssueShellTest {

    private IssueService service;
    private IssueShell shell;

    @BeforeEach
    void setup() {
        service = mock(IssueService.class);
        shell = new IssueShell(service);
    }

    @Test
    void create_shouldDelegateToService() {
        Issue fake = new Issue("AD-1", "desc", null, Status.OPEN,
                OffsetDateTime.now(), null);
        when(service.create("desc", null)).thenReturn(fake);

        Issue out = shell.create("desc", null);

        assertThat(out).isSameAs(fake);
        verify(service).create("desc", null);
    }

    @Test
    void updateStatus_shouldDelegateToService() {
        Issue fake = new Issue("AD-1", "desc", null, Status.CLOSED,
                null, null);
        when(service.updateStatus("AD-1", Status.CLOSED)).thenReturn(fake);

        Issue out = shell.updateStatus("AD-1", Status.CLOSED);

        assertThat(out.status()).isEqualTo(Status.CLOSED);
        verify(service).updateStatus("AD-1", Status.CLOSED);
    }

    @Test
    void list_shouldReturnNoIssuesMessageWhenEmpty() {
        when(service.listByStatus(Status.OPEN)).thenReturn(List.of());

        String out = shell.list("OPEN");

        assertThat(out).isEqualTo("No issues with status OPEN");
    }

    @Test
    void list_shouldJoinIssuesWhenNotEmpty() {
        Issue i1 = new Issue("AD-1", "desc1", null, Status.OPEN,
                OffsetDateTime.now(), null);
        Issue i2 = new Issue("AD-2", "desc2", null, Status.OPEN,
                OffsetDateTime.now(), null);
        when(service.listByStatus(Status.OPEN)).thenReturn(List.of(i1, i2));

        String out = shell.list("OPEN");

        assertThat(out).contains("AD-1");
        assertThat(out).contains("AD-2");
        assertThat(out).contains("\n\n");
    }

    @Test
    void parseStatus_shouldNormalizeAndParse() {
        assertThat(invokeParse("open")).isEqualTo(Status.OPEN);
        assertThat(invokeParse("IN progress")).isEqualTo(Status.IN_PROGRESS);
        assertThat(invokeParse("in-progress")).isEqualTo(Status.IN_PROGRESS);
        assertThat(invokeParse(" CLOSED ")).isEqualTo(Status.CLOSED);
    }

    @Test
    void parseStatus_shouldThrowOnInvalid() {
        assertThatThrownBy(() -> invokeParse("WRONG"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Allowed");
    }

    private Status invokeParse(String s) {
        try {
            var m = IssueShell.class.getDeclaredMethod("parseStatus", String.class);
            m.setAccessible(true);
            return (Status) m.invoke(shell, s);
        } catch (Exception e) {
            if (e.getCause() instanceof RuntimeException re) {
                throw re;
            }
            throw new RuntimeException(e);
        }
    }

}