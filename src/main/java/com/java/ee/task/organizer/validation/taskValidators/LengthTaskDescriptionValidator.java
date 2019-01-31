package com.java.ee.task.organizer.validation.taskValidators;

import com.java.ee.task.organizer.entity.Task;
import com.java.ee.task.organizer.identity.TaskIdentity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class LengthTaskDescriptionValidator implements TaskValidator {
    private final int taskDescriptionMaxLength;

    public LengthTaskDescriptionValidator(@Value("${task.desc.max.length}") int taskDescriptionMaxLength) {
        this.taskDescriptionMaxLength = taskDescriptionMaxLength;
    }

    @Override
    public List<String> validateTask(TaskIdentity taskIdentity, Task task) {
        if (StringUtils.length(task.getDescription()) > taskDescriptionMaxLength) {
            return Collections.singletonList("Task description cannot be longer than " + taskDescriptionMaxLength + " characters.");
        }

        return Collections.emptyList();
    }
}
