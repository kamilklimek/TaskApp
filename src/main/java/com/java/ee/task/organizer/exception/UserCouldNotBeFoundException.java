package com.java.ee.task.organizer.exception;

public class UserCouldNotBeFoundException extends RuntimeException {
    public UserCouldNotBeFoundException() {
    }

    public UserCouldNotBeFoundException(String message) {
        super(message);
    }
}
