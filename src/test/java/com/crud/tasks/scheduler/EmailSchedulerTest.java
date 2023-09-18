package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testSendInformationEmail() {

        //WHEN
        emailScheduler.sendInformationEmail();

        //THEN
        verify(simpleEmailService, times(1)).send(any(Mail.class));
    }

    @Test
    public void testGenerateTaskOrTasksText() {

        // WHEN & THEN

        // Test case when size is 1
        String result1 = emailScheduler.generateTaskOrTasksText(1);
        assertEquals(" task", result1);

        // Test case when size is greater than 1
        String result2 = emailScheduler.generateTaskOrTasksText(5);
        assertEquals(" tasks", result2);
    }
}