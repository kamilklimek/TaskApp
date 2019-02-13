package com.java.ee.task.organizer.validation.userValidators;

import com.java.ee.task.organizer.entity.User;

import java.util.List;

public interface UserValidator {
    List<String> validate(User user);
}
