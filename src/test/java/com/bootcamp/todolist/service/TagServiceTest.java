package com.bootcamp.todolist.service;

import com.bootcamp.todolist.entity.Tag;
import com.bootcamp.todolist.exception.TagNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    void should_call_company_repository_delete_by_id_once_when_delete_given_found_company_id() throws TagNotFoundException {
        //given
        when(this.tagRepository.existsById("1")).thenReturn(true);

        //when
        this.tagService.delete("1");

        //then
        verify(this.tagRepository, times(1)).deleteById("1");
    }

    @Test
    void should_throw_company_not_found_exception_when_delete_given_not_found_company_id() {
        //given
        when(this.tagRepository.existsById("1")).thenReturn(false);

        //then
        assertThrows(TagNotFoundException.class, () -> {
            //then
            this.tagService.delete("1");
        });
    }
}
