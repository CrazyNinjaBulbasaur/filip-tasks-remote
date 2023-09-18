package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
//    @InjectMocks
//    private TaskController taskController;

//    @Mock

    @MockBean
    private DbService dbService;
//    @Mock
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void testGetTasks() throws Exception {
        // Arrange
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
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));


    }

}