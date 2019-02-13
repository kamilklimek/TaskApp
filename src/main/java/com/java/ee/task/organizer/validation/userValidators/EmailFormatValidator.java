package com.java.ee.task.organizer.validation.userValidators;

import com.java.ee.task.organizer.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailFormatValidator implements UserValidator{
    private final EmailValidator emailValidator;


    @Override
    public List<String> validate(User user) {
        if (isEmailNotValid(user.getEmail())) {
            return Collections.singletonList("Email has incorrect format.");
        }

        return Collections.emptyList();
    }

    private boolean isEmailNotValid(String email) {
        return !emailValidator.isValid(email);
    }
}
