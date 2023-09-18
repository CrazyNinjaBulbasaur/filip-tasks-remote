package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    private TaskMapper taskMapper = new TaskMapper();

    @Test
    public void testMapToTask() {

        //GIVEN
        TaskDto taskDto = new TaskDto(1L, "Task Title", "Task Content");

        //WHEN
        Task task = taskMapper.mapToTask(taskDto);

        //THEN
        assertEquals(1L, task.getId());
        assertEquals("Task Title", task.getTitle());
        assertEquals("Task Content", task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //GIVEN
        Task task = new Task(1L, "Task Title", "Task Content");

        //WHEN
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //THEN
        assertEquals(1L, taskDto.getId());
        assertEquals("Task Title", taskDto.getTitle());
        assertEquals("Task Content", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        //GIVEN
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Task 1", "Content 1"));
        taskList.add(new Task(2L, "Task 2", "Content 2"));

        //WHEN
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //THEN
        assertEquals(2, taskDtoList.size());
        assertEquals(1L, taskDtoList.get(0).getId());
        assertEquals("Task 1", taskDtoList.get(0).getTitle());
        assertEquals("Content 1", taskDtoList.get(0).getContent());
        assertEquals(2L, taskDtoList.get(1).getId());
        assertEquals("Task 2", taskDtoList.get(1).getTitle());
        assertEquals("Content 2", taskDtoList.get(1).getContent());
    }
}