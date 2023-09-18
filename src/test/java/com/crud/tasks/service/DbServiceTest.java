package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void shouldGetAllTasks() {
        //GIVEN
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Description 1"));
        tasks.add(new Task(2L, "Task 2", "Description 2"));

        when(taskRepository.findAll()).thenReturn(tasks);

        //WHEN
        List<Task> result = dbService.getAllTasks();

        // THEN
        assertEquals(tasks, result);
    }

    @Test
    public void shouldGetEmptyListOfTasks() {
        //GIVEN
        when(taskRepository.findAll()).thenReturn(List.of());

        //WHEN
        List<Task> result = dbService.getAllTasks();

        //THEN
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void shouldSaveTask() {
        //GIVEN
        Task task = new Task(1L, "Task 1", "Description 1");

        when(taskRepository.save(task)).thenReturn(task);

        //WHEN
        Task result = dbService.saveTask(task);

        //THEN
        assertEquals(task, result);
    }


    @Test
    public void testGetTask() {
        //GIVEN
        Task task = new Task(1L, "Task 1", "Description 1");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //WHEN
        Task result;
        try {
            result = dbService.getTask(1L);
        } catch (TaskNotFoundException e) {
            throw new RuntimeException(e);
        }

        //THEN
        assertEquals(task, result);
    }

    @Test
    public void testGetTaskNotFound() {

        //GIVEN
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        //THEN
        assertThrows(TaskNotFoundException.class, () -> dbService.getTask(1L));

    }

    @Test
    public void testDeleteTask() {
        //GIVEN
        dbService.deleteTask(1L);

        // THEN
        verify(taskRepository, times(1)).deleteById(1L);
    }

}