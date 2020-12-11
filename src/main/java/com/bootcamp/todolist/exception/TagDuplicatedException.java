package com.bootcamp.todolist.exception;

public class TagDuplicatedException extends Exception {
    public TagDuplicatedException() {
        super("Tag existed.");
    }
}
