package com.java.ee.task.organizer.services;

import com.java.ee.task.organizer.dao.UserRepository;
import com.java.ee.task.organizer.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        log.info("Getting all users...");
        return userRepository.findAll();
    }

    public Optional<User> getUserByLogin(String login) {
        log.info("Getting user for login: {}", login);
        return userRepository.findByLogin(login);
    }

    public Long addUser(User user) {
        log.info("Add user, login: {}, email: {}, gender: {}", user.getLogin(), user.getEmail(), user.getGender());
        encodePassword(user);

        return userRepository.save(user).getId();
    }

    public boolean isUserExistByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void encodePassword(User user) {
        log.info("Encoding password for user: {}", user.getLogin());

        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
