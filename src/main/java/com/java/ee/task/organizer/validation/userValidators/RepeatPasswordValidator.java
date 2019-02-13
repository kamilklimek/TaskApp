package com.java.ee.task.organizer.validation.userValidators;

import com.java.ee.task.organizer.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RepeatPasswordValidator implements UserValidator{
    @Override
    public List<String> validate(User user) {
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            return Collections.singletonList("Password are not match.");
        }

        return Collections.emptyList();
    }
}
