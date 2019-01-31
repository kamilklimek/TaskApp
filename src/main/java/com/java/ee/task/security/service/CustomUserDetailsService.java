package com.java.ee.task.security.service;

import com.java.ee.task.organizer.services.UserService;
import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.security.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username with login: " + username + " does not exists."));

        return new CustomUserDetails(username, user.getPassword());
    }


}
