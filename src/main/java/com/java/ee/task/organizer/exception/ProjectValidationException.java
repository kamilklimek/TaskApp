package com.java.ee.task.organizer.exception;

public class ProjectValidationException extends RuntimeException{
    public ProjectValidationException(String join) {
        super(join);
    }
}
