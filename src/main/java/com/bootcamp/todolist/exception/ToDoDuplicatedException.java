package com.bootcamp.todolist.exception;

public class ToDoDuplicatedException extends Exception {
    public ToDoDuplicatedException() {
        super("ToDo existed.");
    }
}
