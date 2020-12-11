package com.bootcamp.todolist.repository;

import com.bootcamp.todolist.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagRepository extends MongoRepository<Tag, String> {
    boolean existsByMessage(String message);
}
