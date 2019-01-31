package com.java.ee.task.organizer;

import com.java.ee.task.organizer.identity.Identity;
import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.organizer.exception.UserCouldNotBeFoundException;
import com.java.ee.task.organizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFacade {

    private final UserService userService;

    @Autowired
    public UserFacade(UserService userService) {
        this.userService = userService;
    }


    public Long registerUser(User user) {
        // validate users
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
