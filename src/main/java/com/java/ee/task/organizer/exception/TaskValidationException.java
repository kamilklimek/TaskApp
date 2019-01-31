package com.java.ee.task.organizer.exception;

public class TaskValidationException extends RuntimeException {
    public TaskValidationException(String join) {
        super(join);
    }
}
