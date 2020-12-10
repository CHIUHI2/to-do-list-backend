package com.bootcamp.todolist.service;

import com.bootcamp.todolist.entity.ToDo;
import com.bootcamp.todolist.exception.ToDoNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    void should_return_correct_todo_when_add_given_not_existed_todo() {
        //given
        ToDo toDo = new ToDo("ToDo");

        when(this.toDoRepository.insert(toDo)).thenReturn(toDo);

        //when
        ToDo returnedToDo = this.toDoService.add(toDo);

        //then
        assertEquals(toDo.getMessage(), returnedToDo.getMessage());
        assertEquals(toDo.getTagIds(), returnedToDo.getTagIds());
    }

    @Test
    void should_return_replaced_todo_when_replace_given_found_id() throws ToDoNotFoundException {
        //given
        ToDo toDo = new ToDo("ToDO");

        when(this.toDoRepository.existsById("1")).thenReturn(true);
        when(this.toDoRepository.save(toDo)).thenReturn(toDo);

        //when
        ToDo returnedToDo = this.toDoService.replace("1", toDo);

        //then
        assertEquals("1", returnedToDo.getId());
        assertEquals(toDo.getMessage(), returnedToDo.getMessage());
        assertEquals(toDo.getTagIds(), returnedToDo.getTagIds());
    }

    @Test
    void should_throw_todo_not_found_exception_when_replace_given_not_found_id() {
        //given
        ToDo toDo = new ToDo("ToDO");

        when(this.toDoRepository.existsById("1")).thenReturn(false);

        //then
        assertThrows(ToDoNotFoundException.class, () -> {
            //when
            this.toDoService.replace("1", toDo);
        });
    }

    @Test
    void should_call_todo_repository_delete_by_id_once_when_delete_given_found_id() throws ToDoNotFoundException {
        //given
        when(this.toDoRepository.existsById("1")).thenReturn(true);

        //when
        this.toDoService.delete("1");

        //then
        verify(this.toDoRepository, times(1)).deleteById("1");
    }

    @Test
    void should_throw_todo_not_found_exception_when_delete_given_not_found_id() {
        //given
        when(this.toDoRepository.existsById("1")).thenReturn(false);

        //then
        assertThrows(ToDoNotFoundException.class, () -> {
            //then
            this.toDoService.delete("1");
        });
    }
}
