package com.bootcamp.todolist.exception;

public class ToDoNotFoundException extends Exception {
    public ToDoNotFoundException() {
        super("ToDo not found.");
    }
}
