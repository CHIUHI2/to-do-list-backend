package com.bootcamp.todolist.service;

import com.bootcamp.todolist.entity.ToDo;
import com.bootcamp.todolist.repository.ToDoRepository;
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
public class ToDoServiceTest {
    @Mock
    ToDoRepository toDoRepository;

    @InjectMocks
    ToDoService toDoService;

    @Test
    void should_return_all_todos_when_get_all_given_all_todos() {
        //given
        List<ToDo> toDos = Arrays.asList(
                new ToDo("ToDo1"),
                new ToDo("ToDo2")
        );

        when(this.toDoRepository.findAll()).thenReturn(toDos);

        //when
        List<ToDo> returnedToDos = this.toDoService.findAll();

        //then
        assertEquals(toDos, returnedToDos);
    }
}
