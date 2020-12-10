package com.bootcamp.todolist.service;

import com.bootcamp.todolist.entity.Tag;
import com.bootcamp.todolist.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagService tagService;

    @Test
    void should_return_all_tags_when_find_all_given_all_tags() {
        //given
        List<Tag> tags = Arrays.asList(
                new Tag("Tag1", "red"),
                new Tag("Tag2", "green")
        );

        when(this.tagRepository.findAll()).thenReturn(tags);

        //when
        List<Tag> returnedTags = this.tagService.findAll();

        //then
        assertEquals(tags, returnedTags);
    }

    @Test
    void should_return_correct_tag_when_add_given_not_existed_tag() {
        //given
        Tag tag = new Tag("Tag1", "red");

        when(this.tagRepository.insert(tag)).thenReturn(tag);

        //when
        Tag returnedTag = this.tagService.add(tag);

        //then
        assertEquals(tag.getMessage(), returnedTag.getMessage());
        assertEquals(tag.getColor(), returnedTag.getColor());
    }
}
