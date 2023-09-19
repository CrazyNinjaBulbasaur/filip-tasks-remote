package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchZeroTasks() throws Exception {
        // GIVEN
        when(taskMapper.mapToTaskDtoList(any(List.class))).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));


    }

    @Test
    public void shouldFetchTasks() throws Exception {
        // GIVEN
        List<Task> tasks = List.of(new Task(1L, "Task 1", "Description 1"));
        List<TaskDto> taskDtos = List.of(new TaskDto(1L, "Task 1", "Description 1"));

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))

                // Task fields
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Description 1")));
    }

    @Test
    public void shouldGetTaskByID() throws Exception {
        // GIVEN
        long taskId = 1L;
        Task task = new Task(taskId, "Task 1", "Description 1");
        TaskDto taskDto = new TaskDto(taskId, "Task 1", "Description 1");

        when(dbService.getTask(taskId)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Description 1")));
    }

    @Test
    public void shouldDeleteTaskById() throws Exception{
        //GIVEN
        long taskId = 1L;

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)); // or isOk()

    }

    @Test
    public void shouldNotDeleteTaskById() throws Exception{
        //GIVEN
        long taskId = 1L;

        //When & Then
        Mockito.doThrow(new TaskNotFoundException()).when(dbService).deleteTask(taskId);


        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().is(400));
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void shouldUpdateTask() throws Exception{
        //GIVEN
        TaskDto taskDto = new TaskDto(1L, "Task 1", "Description 1");
        Task task = new Task(1L, "Task 1", "Description 1");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.saveTask(task)).thenReturn(task);


        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task 1")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Description 1")));
    }

    @Test
    public void shouldCreateTask() throws Exception{
        //GIVEN
        TaskDto taskDto = new TaskDto(1L, "Task 1", "Description 1");
        Task task = new Task(1L, "Task 1", "Description 1");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
