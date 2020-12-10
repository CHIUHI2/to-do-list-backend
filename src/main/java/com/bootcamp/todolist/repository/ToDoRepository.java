package com.bootcamp.todolist.repository;

import com.bootcamp.todolist.entity.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToDoRepository extends MongoRepository<ToDo, String> {
}
