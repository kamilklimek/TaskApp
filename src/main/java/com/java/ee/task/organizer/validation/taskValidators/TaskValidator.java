package com.java.ee.task.organizer.validation.taskValidators;

import com.java.ee.task.organizer.entity.Task;
import com.java.ee.task.organizer.identity.TaskIdentity;

import java.util.List;

public interface TaskValidator {
    List<String> validateTask(TaskIdentity taskIdentity, Task task);
}
