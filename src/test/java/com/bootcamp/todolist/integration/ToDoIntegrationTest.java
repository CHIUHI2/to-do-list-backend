package com.bootcamp.todolist.integration;

import com.bootcamp.todolist.entity.ToDo;
import com.bootcamp.todolist.repository.ToDoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    private static final String apiBaseUrl = "/todos";

    @AfterEach
    void tearDown() {
        this.toDoRepository.deleteAll();
    }

    @Test
    void should_return_all_todos_when_get_all_given_todos() throws Exception {
        //given
        ToDo toDo1 = new ToDo("ToDo1");
        this.toDoRepository.save(toDo1);

        ToDo toDo2 = new ToDo("ToDo2");
        this.toDoRepository.save(toDo2);

        //when
        //then
        this.mockMvc.perform(get(apiBaseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].message").value("ToDo1"))
                .andExpect(jsonPath("$[0].done").isBoolean())
                .andExpect(jsonPath("$[0].tags").isEmpty())
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].message").value("ToDo2"))
                .andExpect(jsonPath("$[1].done").isBoolean())
                .andExpect(jsonPath("$[1].tags").isEmpty());;
    }
}
