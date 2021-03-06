package com.bootcamp.todolist.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;

@Document
public class ToDo {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String message;
    private boolean done;
    private Set<String> tags;

    public ToDo() {
        this.done = false;
        this.tags = new HashSet<>();
    }

    public ToDo(String message) {
        this.message = message;
        this.done = false;
        this.tags = new HashSet<>();;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
