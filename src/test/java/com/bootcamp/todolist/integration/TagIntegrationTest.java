package com.bootcamp.todolist.integration;

import com.bootcamp.todolist.entity.Tag;
import com.bootcamp.todolist.entity.ToDo;
import com.bootcamp.todolist.repository.TagRepository;
import com.bootcamp.todolist.repository.ToDoRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TagIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ToDoRepository toDoRepository;

    private static final String apiBaseUrl = "/tags";

    @AfterEach
    void tearDown() {
        this.tagRepository.deleteAll();
    }

    @Test
    void should_return_all_tags_when_get_all_given_tags() throws Exception {
        //given
        Tag tag1 = new Tag("Tag1", "red");
        this.tagRepository.save(tag1);

        Tag tag2 = new Tag("Tag2", "green");
        this.tagRepository.save(tag2);

        //when
        //then
        this.mockMvc.perform(get(apiBaseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].message").value("Tag1"))
                .andExpect(jsonPath("$[0].color").value("red"))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].message").value("Tag2"))
                .andExpect(jsonPath("$[1].color").value("green"));
    }

    @Test
    void should_return_added_tag_when_add_given_not_existed_tag() throws Exception {
        //given
        JSONObject requestBody = new JSONObject();
        requestBody.put("message", "Tag1");
        requestBody.put("color", "red");

        //when
        //then
        this.mockMvc.perform(post(apiBaseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString())
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.message").value("Tag1"))
                .andExpect(jsonPath("$.color").value("red"));

        List<Tag> tags = this.tagRepository.findAll();
        assertEquals(1, tags.size());
        assertEquals("Tag1", tags.get(0).getMessage());
        assertEquals("red", tags.get(0).getColor());
    }

    @Test
    void should_return_204_when_delete_given_found_id() throws Exception {
        //given
        Tag tag1 = new Tag("Tag1", "red");
        Tag addedTag1 = this.tagRepository.save(tag1);

        Tag tag2 = new Tag("Tag2", "green");
        Tag addedTag2 = this.tagRepository.save(tag2);

        ToDo toDo = new ToDo("ToDo");
        toDo.setTags(new HashSet<>(Arrays.asList(addedTag1.getId(), addedTag2.getId())));
        this.toDoRepository.save(toDo);

        //when
        //then
        this.mockMvc.perform(delete(apiBaseUrl + "/" + addedTag1.getId()))
                .andExpect(status().isNoContent());

        List<Tag> tags = this.tagRepository.findAll();
        assertEquals(1, tags.size());
        assertEquals(addedTag2.getId(), tags.get(0).getId());

        List<ToDo> toDos = this.toDoRepository.findAll();
        assertEquals(Collections.singleton(tag2.getId()), toDos.get(0).getTags());
    }
    @Test
    void should_return_404_when_delete_given_not_found_id() throws Exception {
        //given
        Tag tag1 = new Tag("Tag1", "red");
        Tag addedTag1 = this.tagRepository.save(tag1);

        Tag tag2 = new Tag("Tag2", "green");
        Tag addedTag2 = this.tagRepository.save(tag2);

        this.tagRepository.deleteById(addedTag1.getId());

        //when
        //then
        this.mockMvc.perform(delete("/employees/" + addedTag1.getId()))
                .andExpect(status().isNotFound());

        List<Tag> tags = this.tagRepository.findAll();
        assertEquals(1, tags.size());
        assertEquals(addedTag2.getId(), tags.get(0).getId());
    }
}
