package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(TaskController.class)
@ExtendWith(SpringExtension.class)
class TaskControllerTest2 {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TaskController taskController;

    @MockBean
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;


    @Test
    public void testUpdateTask() {
        // Arrange
        TaskDto taskDto = new TaskDto(1L, "Task 1", "Description 1");
        Task task = new Task(1L, "Task 1", "Description 1");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        // Act
        ResponseEntity<TaskDto> responseEntity = taskController.updateTask(taskDto);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(taskDto, responseEntity.getBody());
    }

    @Test
    public void testCreateTask() {
        // Arrange
        TaskDto taskDto = new TaskDto(1L, "Task 1", "Description 1");
        Task task = new Task(1L, "Task 1", "Description 1");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        // Act
        ResponseEntity<Void> responseEntity = taskController.createTask(taskDto);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}