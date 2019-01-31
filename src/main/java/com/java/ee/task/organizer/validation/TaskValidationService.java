package com.java.ee.task.organizer.validation;

import com.java.ee.task.organizer.entity.Task;
import com.java.ee.task.organizer.exception.TaskValidationException;
import com.java.ee.task.organizer.identity.TaskIdentity;
import com.java.ee.task.organizer.validation.taskValidators.TaskValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class TaskValidationService {
    private final List<TaskValidator> taskValidators;

    public void validate(TaskIdentity taskIdentity, Task task) {
        log.debug("Validating task: {}, task identity: {} ", task, taskIdentity);

        final List<String> collect = taskValidators.stream()
                .map(validator -> validator.validateTask(taskIdentity, task))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            log.debug("Tasks validation has some errors: {}", collect);
            throw new TaskValidationException(String.join(",", collect));
        }

    }

}
