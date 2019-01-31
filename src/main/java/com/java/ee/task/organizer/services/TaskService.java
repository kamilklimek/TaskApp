package com.java.ee.task.organizer.services;

import com.java.ee.task.organizer.dao.TaskRepository;
import com.java.ee.task.organizer.entity.Task;
import com.java.ee.task.organizer.exception.TaskNotFoundException;
import com.java.ee.task.organizer.identity.TaskIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    public Long saveTask(Task task) {
        log.info("Save task: {} in database.", task);
        return taskRepository.save(task).getId();
    }

    public Task getTask(TaskIdentity taskIdentity) {
        return taskRepository.findById(taskIdentity.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException("Task for id: " + taskIdentity.getTaskId() + " does not exist."));
    }

    public Task getTaskByName(Task task) {
        return taskRepository.findByName(task.getName())
                .orElseThrow(() -> new TaskNotFoundException("Task for name: " + task.getName() + " does not exist."));
    }

    public boolean isTaskExists(TaskIdentity taskIdentity) {
        return taskRepository.existsById(taskIdentity.getTaskId());
    }

    public boolean isTasksBelongsToUser(TaskIdentity taskIdentity) {
        return getTask(taskIdentity)
                .getProject().getUser().getLogin()
                .equals(taskIdentity.getLogin());
    }

    public void removeTask(TaskIdentity taskIdentity) {
        if (isTaskExists(taskIdentity)) {
            taskRepository.deleteById(taskIdentity.getTaskId());
            return;
        }

        throw new TaskNotFoundException("Task for id: " + taskIdentity.getTaskId() + " does not exist.");
    }

    public boolean isUserProjectHasAlreadyTaskName(TaskIdentity taskIdentity, Task task) {
        try {
            Task taskFromDb = getTaskByName(task);

            return taskFromDb.getProject().getUser().getLogin().equals(taskIdentity.getLogin());
        } catch (Exception e) {
            log.info("Task with name: " + task.getName() + " does not exists.");
            return false;
        }
    }
}
