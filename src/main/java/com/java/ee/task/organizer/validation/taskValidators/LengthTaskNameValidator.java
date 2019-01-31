package com.java.ee.task.organizer.validation.taskValidators;

import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.entity.Task;
import com.java.ee.task.organizer.identity.ProjectIdentity;
import com.java.ee.task.organizer.identity.TaskIdentity;
import com.java.ee.task.organizer.validation.projectValidators.ProjectValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LengthTaskNameValidator implements TaskValidator {
    private final int taskNameMaxLength;
    private final int taskNameMinLength;

    public LengthTaskNameValidator(@Value("${task.name.min.length}") int taskNameMinLength,
                                   @Value("${task.name.max.length}") int taskNameMaxLength) {

        this.taskNameMaxLength = taskNameMaxLength;
        this.taskNameMinLength = taskNameMinLength;
    }


    @Override
    public List<String> validateTask(TaskIdentity taskIdentity, Task task) {
        if (StringUtils.isEmpty(task.getName())) {
            return Collections.singletonList("Task name cannot be empty.");
        }

        if (StringUtils.length(task.getName()) > taskNameMaxLength) {
            return Collections.singletonList("Task name cannot be longer than " + taskNameMaxLength + " characters.");
        }


        if (StringUtils.length(task.getName()) < taskNameMinLength) {
            return Collections.singletonList("Task name cannot be shorter than " + taskNameMinLength + " characters.");
        }

        return Collections.emptyList();
    }
}
