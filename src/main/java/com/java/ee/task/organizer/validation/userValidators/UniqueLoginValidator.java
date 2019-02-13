package com.java.ee.task.organizer.validation.userValidators;

import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.organizer.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UniqueLoginValidator implements UserValidator{
    private final UserService userService;


    @Override
    public List<String> validate(User user) {
        if (userService.isUserExistByLogin(user.getLogin())) {
            return Collections.singletonList("Login already exists.");
        }

        return Collections.emptyList();
    }

}
