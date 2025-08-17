package com.xshevska.issuetracker.domain.service.impl;

import com.xshevska.issuetracker.domain.model.Issue;
import com.xshevska.issuetracker.domain.model.Status;
import com.xshevska.issuetracker.port.out.SpreadsheetFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DefaultIssueServiceTest {

    private SpreadsheetFacade facade;
    private DefaultIssueService service;

    @BeforeEach
    void setup() {
        facade = mock(SpreadsheetFacade.class);
        service = new DefaultIssueService(facade);
    }

    @Test
    void create_shouldGenerateIdAndSaveIssue() {
        when(facade.maxNumericId("AD-")).thenReturn(5);
        when(facade.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Issue issue = service.create("desc", "AD-1");
        assertThat(issue.id()).isEqualTo("AD-6");
        assertThat(issue.status()).isEqualTo(Status.OPEN);
        assertThat(issue.updatedAt()).isNull();
        verify(facade).save(issue);
    }

    @Test
    void updateStatus_shouldDelegateToFacade() {
        Issue fake = new Issue("AD-1", "desc", null, Status.CLOSED, null, null);
        when(facade.updateStatus("AD-1", Status.CLOSED)).thenReturn(fake);
        Issue out = service.updateStatus("AD-1", Status.CLOSED);
        assertThat(out.status()).isEqualTo(Status.CLOSED);
        verify(facade).updateStatus("AD-1", Status.CLOSED);
    }

    @Test
    void listByStatus_shouldDelegateToFacade() {
        when(facade.findByStatus(Status.OPEN)).thenReturn(List.of());
        var result = service.listByStatus(Status.OPEN);
        assertThat(result).isEmpty();
        verify(facade).findByStatus(Status.OPEN);
    }
}

