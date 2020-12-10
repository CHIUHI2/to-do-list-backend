package com.bootcamp.todolist.service;

import com.bootcamp.todolist.entity.ToDo;
import com.bootcamp.todolist.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    public List<ToDo> findAll() {
        return this.toDoRepository.findAll();
    }

    public ToDo add(ToDo toDo) {
        return this.toDoRepository.insert(toDo);
    }
}
