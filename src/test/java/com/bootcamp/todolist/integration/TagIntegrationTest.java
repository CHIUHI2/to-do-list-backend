package com.bootcamp.todolist.integration;

import com.bootcamp.todolist.entity.Tag;
import com.bootcamp.todolist.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
}
