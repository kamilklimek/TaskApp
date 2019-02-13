package com.java.ee.task.organizer.validation.userValidators;

import com.java.ee.task.organizer.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LengthLoginValidator implements UserValidator{
    private final int minLengthLoginLength;
    private final int maxLengthLoginLength;

    public LengthLoginValidator(@Value("${user.login.min.length}") int minLengthLoginLength,
                                @Value("${user.login.max.length}") int maxLengthLoginLength) {
        this.minLengthLoginLength = minLengthLoginLength;
        this.maxLengthLoginLength = maxLengthLoginLength;
    }

    @Override
    public List<String> validate(User user) {
        int loginLength = user.getLogin().length();

        if (user.getLogin().isEmpty()) {
            return Collections.singletonList("Login cannot be empty.");
        }

        if (loginLength > maxLengthLoginLength) {
            return Collections.singletonList("Login cannot be longer than " + maxLengthLoginLength + " characters.");
        }

        if (loginLength < minLengthLoginLength) {
            return Collections.singletonList("Login cannot be shorter than " + minLengthLoginLength + " characters.");
        }

        return Collections.emptyList();
    }
}
