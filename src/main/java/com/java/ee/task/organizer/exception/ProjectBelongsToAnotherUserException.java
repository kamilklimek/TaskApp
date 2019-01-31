package com.java.ee.task.organizer.exception;

public class ProjectBelongsToAnotherUserException extends RuntimeException {
    public ProjectBelongsToAnotherUserException(String s) {
        super(s);
    }
}
