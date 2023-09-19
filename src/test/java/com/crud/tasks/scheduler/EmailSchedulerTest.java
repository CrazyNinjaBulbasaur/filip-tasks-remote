package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private AdminConfig adminConfig;

    @Test
    public void testSendInformationEmailWithOneTask() {
        //GIVEN
        long size = 1L;
        when(taskRepository.count()).thenReturn(size);

        // WHEN
        emailScheduler.sendInformationEmail();

        //THEN
        verify(simpleEmailService, times(1)).send(any(Mail.class));

        // Capture the argument passed to simpleEmailService.send
        ArgumentCaptor<Mail> mailArgumentCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(simpleEmailService).send(mailArgumentCaptor.capture());

        // verifying Mail fields
        Mail capturedMail = mailArgumentCaptor.getValue();
        assertEquals("Tasks: Once a day email", capturedMail.getSubject());
        assertEquals("Currently in database you got: " + size + " task", capturedMail.getMessage());
    }

    @Test
    public void testSendInformationEmailWithZeroTask() {
        //GIVEN
        long size = 0L;
        when(taskRepository.count()).thenReturn(size);

        // WHEN
        emailScheduler.sendInformationEmail();

        //THEN
        verify(simpleEmailService, times(1)).send(any(Mail.class));

        // Capture the argument passed to simpleEmailService.send
        ArgumentCaptor<Mail> mailArgumentCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(simpleEmailService).send(mailArgumentCaptor.capture());

        // verifying Mail fields
        Mail capturedMail = mailArgumentCaptor.getValue();
        assertEquals("Tasks: Once a day email", capturedMail.getSubject());
        assertEquals("Currently in database you got: " + size + " tasks", capturedMail.getMessage());
    }

    @Test
    public void testSendInformationEmailWithMoreThanOneTasks() {
        //GIVEN
        long size = 3L;
        when(taskRepository.count()).thenReturn(size);

        // WHEN
        emailScheduler.sendInformationEmail();

        //THEN
        verify(simpleEmailService, times(1)).send(any(Mail.class));

        // Capture the argument passed to simpleEmailService.send
        ArgumentCaptor<Mail> mailArgumentCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(simpleEmailService).send(mailArgumentCaptor.capture());

        // verifying Mail fields
        Mail capturedMail = mailArgumentCaptor.getValue();
        assertEquals("Tasks: Once a day email", capturedMail.getSubject());
        assertEquals("Currently in database you got: " + size + " tasks", capturedMail.getMessage());
    }
}