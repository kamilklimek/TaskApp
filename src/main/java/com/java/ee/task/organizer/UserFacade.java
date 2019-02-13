package com.java.ee.task.organizer;

import com.java.ee.task.organizer.identity.Identity;
import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.organizer.exception.UserCouldNotBeFoundException;
import com.java.ee.task.organizer.services.UserService;
import com.java.ee.task.organizer.validation.UserValidationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserFacade {
    private final UserValidationService userValidationService;
    private final UserService userService;


    public Long registerUser(User user) {
        userValidationService.validate(user);

        return userService.addUser(user);
    }

    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    public User getCurrentLoggedUser(Identity identity) {
        return userService.getUserByLogin(identity.getLogin())
                .orElseThrow(UserCouldNotBeFoundException::new);
    }
}
