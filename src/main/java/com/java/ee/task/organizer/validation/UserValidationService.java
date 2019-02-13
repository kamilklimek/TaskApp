package com.java.ee.task.organizer.validation;

import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.organizer.exception.UserValidationException;
import com.java.ee.task.organizer.validation.userValidators.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserValidationService {
    private final List<UserValidator> userValidators;

    public void validate(User user) {
        log.debug("Validating user: {}", user);

        final List<String> collect = userValidators.stream()
                .map(validator -> validator.validate(user))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            log.debug("User validation has some errors: {}", collect);
            throw new UserValidationException(String.join(" ", collect));
        }
    }
}
