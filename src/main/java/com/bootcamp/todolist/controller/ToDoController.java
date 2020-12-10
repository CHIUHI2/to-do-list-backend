package com.bootcamp.todolist.controller;

import com.bootcamp.todolist.dto.ToDoRequest;
import com.bootcamp.todolist.dto.ToDoResponse;
import com.bootcamp.todolist.mapper.ToDoMapper;
import com.bootcamp.todolist.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @Autowired
    private ToDoMapper toDoMapper;

    @GetMapping
    public List<ToDoResponse> findAll() {
        return this.toDoMapper.toResponse(this.toDoService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoResponse add(@RequestBody ToDoRequest toDoRequest) {
        return this.toDoMapper.toResponse(this.toDoService.add(this.toDoMapper.toEntity(toDoRequest)));
    }
}
