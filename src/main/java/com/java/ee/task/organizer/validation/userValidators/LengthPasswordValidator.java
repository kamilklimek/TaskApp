package com.java.ee.task.organizer.validation.userValidators;

import com.java.ee.task.organizer.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LengthPasswordValidator implements UserValidator{
    private final int minLengthPasswordLength;
    private final int maxLengthPasswordLength;

    public LengthPasswordValidator(@Value("${user.password.min.length}") int minLengthLoginLength,
                                   @Value("${user.password.max.length}") int maxLengthLoginLength) {
        this.minLengthPasswordLength = minLengthLoginLength;
        this.maxLengthPasswordLength = maxLengthLoginLength;
    }

    @Override
    public List<String> validate(User user) {
        int passwordLength = user.getPassword().length();

        if (user.getPassword().isEmpty()) {
            return Collections.singletonList("Password cannot be empty.");
        }

        if (passwordLength > maxLengthPasswordLength) {
            return Collections.singletonList("Password cannot be longer than " + maxLengthPasswordLength + " characters.");
        }

        if (passwordLength < minLengthPasswordLength) {
            return Collections.singletonList("Password cannot be shorter than " + minLengthPasswordLength + " characters.");
        }

        return Collections.emptyList();
    }
}
