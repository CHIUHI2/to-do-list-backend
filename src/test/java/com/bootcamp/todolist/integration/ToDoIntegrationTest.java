package com.bootcamp.todolist.integration;

import com.bootcamp.todolist.entity.ToDo;
import com.bootcamp.todolist.repository.ToDoRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Test
    void should_return_added_todo_when_add_given_not_existed_todo() throws Exception {
        //given
        JSONObject requestBody = new JSONObject();
        requestBody.put("message", "ToDo");

        //when
        //then
        this.mockMvc.perform(post(apiBaseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.message").value("ToDo"))
                .andExpect(jsonPath("$.done").isBoolean())
                .andExpect(jsonPath("$.tags").isEmpty());

        List<ToDo> toDos = this.toDoRepository.findAll();
        assertEquals(1, toDos.size());
        assertEquals("ToDo", toDos.get(0).getMessage());
        assertFalse(toDos.get(0).isDone());
        assertEquals(Collections.emptySet(), toDos.get(0).getTags());
    }

    @Test
    void should_return_replaced_todo_when_replace_given_found_id() throws Exception {
        //given
        ToDo toDo = new ToDo("ToDo");
        ToDo addedToDo = this.toDoRepository.save(toDo);

        JSONObject requestBody = new JSONObject();
        requestBody.put("message", "ToDo1");

        //when
        //then
        this.mockMvc.perform(put(apiBaseUrl + "/" + addedToDo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody.toString())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.message").value("ToDo1"))
                .andExpect(jsonPath("$.done").isBoolean())
                .andExpect(jsonPath("$.tags").isEmpty());

        List<ToDo> toDos = this.toDoRepository.findAll();
        assertEquals(1, toDos.size());
        assertEquals(addedToDo.getId(), toDos.get(0).getId());
        assertEquals("ToDo1", toDos.get(0).getMessage());
        assertFalse(toDos.get(0).isDone());
        assertEquals(Collections.emptySet(), toDos.get(0).getTags());
    }

    @Test
    void should_return_404_when_replace_given_not_found_id_and_employee() throws Exception {
        //given
        ToDo toDo = new ToDo("ToDo");
        ToDo addedToDo = this.toDoRepository.save(toDo);
        this.toDoRepository.deleteAll();

        JSONObject requestBody = new JSONObject();
        requestBody.put("message", "ToDo1");

        //when
        //then
        this.mockMvc.perform(put(apiBaseUrl + "/" + addedToDo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                ).andExpect(status().isNotFound());

        List<ToDo> toDos = this.toDoRepository.findAll();
        assertEquals(0, toDos.size());
    }

    @Test
    void should_return_204_when_delete_given_found_id() throws Exception {
        //given
        ToDo toDo1 = new ToDo("ToDo1");
        ToDo addedToDo1 = this.toDoRepository.save(toDo1);

        ToDo toDo2 = new ToDo("ToDo2");
        ToDo addedToDo2 = this.toDoRepository.save(toDo2);

        //when
        //then
        this.mockMvc.perform(delete(apiBaseUrl + "/" + addedToDo1.getId()))
                .andExpect(status().isNoContent());

        List<ToDo> toDos = this.toDoRepository.findAll();
        assertEquals(1, toDos.size());
        assertEquals(addedToDo2.getId(), toDos.get(0).getId());
    }

    @Test
    void should_return_404_when_delete_given_not_found_id() throws Exception {
        //given
        ToDo toDo1 = new ToDo("ToDo1");
        ToDo addedToDo1 = this.toDoRepository.save(toDo1);

        ToDo toDo2 = new ToDo("ToDo2");
        ToDo addedToDo2 = this.toDoRepository.save(toDo2);

        this.toDoRepository.deleteById(addedToDo1.getId());

        //when
        //then
        this.mockMvc.perform(delete("/employees/" + addedToDo1.getId()))
                .andExpect(status().isNotFound());

        List<ToDo> toDos = this.toDoRepository.findAll();
        assertEquals(1, toDos.size());
        assertEquals(addedToDo2.getId(), toDos.get(0).getId());
    }
}
