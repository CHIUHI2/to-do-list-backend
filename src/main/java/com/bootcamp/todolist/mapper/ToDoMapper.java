package com.bootcamp.todolist.mapper;

import com.bootcamp.todolist.dto.ToDoRequest;
import com.bootcamp.todolist.dto.ToDoResponse;
import com.bootcamp.todolist.entity.ToDo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ToDoMapper {
    public ToDo toEntity(ToDoRequest toDoRequest) {
        ToDo toDo = new ToDo();

        BeanUtils.copyProperties(toDoRequest, toDo);

        return toDo;
    }

    public ToDoResponse toResponse(ToDo toDo) {
        ToDoResponse toDoResponse = new ToDoResponse();

        BeanUtils.copyProperties(toDo, toDoResponse);

        return toDoResponse;
    }

    public List<ToDoResponse> toResponse(List<ToDo> toDos) {
        return toDos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
