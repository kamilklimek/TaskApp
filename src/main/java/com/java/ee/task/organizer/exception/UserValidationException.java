package com.java.ee.task.organizer.exception;

public class UserValidationException extends RuntimeException{
    public UserValidationException(String join) {
        super(join);
    }
}
