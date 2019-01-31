package com.java.ee.task.organizer.validation.taskValidators;

import com.java.ee.task.organizer.entity.Task;
import com.java.ee.task.organizer.identity.TaskIdentity;
import com.java.ee.task.organizer.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class UniqueTaskNamePerUserProjectValidator implements TaskValidator{
    private final TaskService taskService;

    @Override
    public List<String> validateTask(TaskIdentity taskIdentity, Task task) {
        if (taskService.isUserProjectHasAlreadyTaskName(taskIdentity, task)) {
            return Collections.singletonList("Task name " + task.getName() + " is already used.");
        }

        return Collections.emptyList();
    }

}
