package com.bootcamp.todolist.repository;

import com.bootcamp.todolist.entity.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ToDoRepository extends MongoRepository<ToDo, String> {
    List<ToDo> findByTags(String id);
    boolean existsByMessage(String message);
}
