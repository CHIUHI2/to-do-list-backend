package com.bootcamp.todolist.exception;

public class TagNotFoundException extends Exception {
    public TagNotFoundException() {
        super("Tag Not Found");
    }
}
