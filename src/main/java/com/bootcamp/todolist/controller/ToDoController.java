package com.bootcamp.todolist.controller;

import com.bootcamp.todolist.dto.ToDoRequest;
import com.bootcamp.todolist.dto.ToDoResponse;
import com.bootcamp.todolist.exception.ToDoDuplicatedException;
import com.bootcamp.todolist.exception.ToDoNotFoundException;
import com.bootcamp.todolist.mapper.ToDoMapper;
import com.bootcamp.todolist.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
@CrossOrigin
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
    public ToDoResponse add(@RequestBody ToDoRequest toDoRequest) throws ToDoDuplicatedException {
        return this.toDoMapper.toResponse(this.toDoService.add(this.toDoMapper.toEntity(toDoRequest)));
    }

    @PutMapping("/{id}")
    public ToDoResponse replace(@PathVariable String id, @RequestBody ToDoRequest toDoRequest) throws ToDoNotFoundException {
        return this.toDoMapper.toResponse(this.toDoService.replace(id, this.toDoMapper.toEntity(toDoRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws ToDoNotFoundException {
        this.toDoService.delete(id);
    }
}
