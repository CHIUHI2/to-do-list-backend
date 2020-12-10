package com.bootcamp.todolist.integration;

import com.bootcamp.todolist.entity.Tag;
import com.bootcamp.todolist.repository.TagRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static final String apiBaseUrl = "/tags";

    @BeforeEach
    void setUp() {
        Tag tag1 = new Tag("Tag1", "red");
        this.tagRepository.save(tag1);

        Tag tag2 = new Tag("Tag2", "green");
        this.tagRepository.save(tag2);
    }

    @AfterEach
    void tearDown() {
        this.tagRepository.deleteAll();
    }

    @Test
    void should_return_all_tags_when_get_all_given_tags() throws Exception {
        //given
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
        this.tagRepository.deleteAll();

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
}
