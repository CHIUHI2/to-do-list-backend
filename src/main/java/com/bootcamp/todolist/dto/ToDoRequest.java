package com.bootcamp.todolist.dto;

import java.util.HashSet;
import java.util.Set;

public class ToDoRequest {
    private String message;
    private boolean done;
    private Set<String> tags;

    ToDoRequest() {
        this.done = false;
        this.tags = new HashSet<>();
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
