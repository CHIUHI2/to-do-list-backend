package com.bootcamp.todolist.controller;

import com.bootcamp.todolist.dto.ToDoResponse;
import com.bootcamp.todolist.mapper.ToDoMapper;
import com.bootcamp.todolist.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<ToDoResponse> getAll() {
        return this.toDoMapper.toResponse(this.toDoService.findAll());
    }
}
