package com.java.ee.task.organizer.exception;

public class ProjectCouldNotBeFoundException extends RuntimeException {
    public ProjectCouldNotBeFoundException() {
    }

    public ProjectCouldNotBeFoundException(String message) {
        super(message);
    }
}
