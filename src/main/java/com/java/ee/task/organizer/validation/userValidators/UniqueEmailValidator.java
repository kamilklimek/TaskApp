package com.java.ee.task.organizer.validation.userValidators;

import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.organizer.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UniqueEmailValidator implements UserValidator{
    private final UserService userService;


    @Override
    public List<String> validate(User user) {
        if (userService.isUserExistByEmail(user.getEmail())) {
            return Collections.singletonList("Email already exists.");
        }

        return Collections.emptyList();
    }

}
