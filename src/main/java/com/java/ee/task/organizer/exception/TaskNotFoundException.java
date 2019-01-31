package com.java.ee.task.organizer.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
