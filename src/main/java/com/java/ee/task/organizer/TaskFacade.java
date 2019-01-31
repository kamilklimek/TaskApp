package com.java.ee.task.organizer;

import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.entity.Task;
import com.java.ee.task.organizer.exception.TaskBelongsToAnotherUserException;
import com.java.ee.task.organizer.identity.TaskIdentity;
import com.java.ee.task.organizer.services.ProjectService;
import com.java.ee.task.organizer.services.TaskService;
import com.java.ee.task.organizer.validation.TaskValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskFacade {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final TaskValidationService taskValidationService;

    public TaskFacade(TaskService taskService, ProjectService projectService, TaskValidationService taskValidationService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.taskValidationService = taskValidationService;
    }

    public Long addTask(TaskIdentity taskIdentity, Task task) {
        taskValidationService.validate(taskIdentity, task);

        projectService.validateProjectBelongsToUser(taskIdentity);

        assigneeProjectToTask(taskIdentity, task);

        return taskService.saveTask(task);
    }

    private void assigneeProjectToTask(TaskIdentity projectIdentity, Task task) {
        log.info("Assignee project to task which is creating. {}", task);
        Project project = Project.builder().id(projectIdentity.getProjectId()).build();
        task.setProject(project);
    }


    public Task getTaskId(TaskIdentity taskIdentity) {
        log.info("Get task for task identity: {}", taskIdentity);

        validateTaskBelongsToUser(taskIdentity);

        try {
            return taskService.getTask(taskIdentity);
        } catch (Exception e) {
            log.error("Error while get task by id for task identity: {}", taskIdentity);
            throw e;
        }
    }

    public void removeTask(TaskIdentity taskIdentity) {
        log.info("Deleting task for task identity: {}", taskIdentity);

        validateTaskBelongsToUser(taskIdentity);

        try {
            taskService.removeTask(taskIdentity);
        } catch (Exception e) {
            log.error("Error while remove task for task identity: {}", taskIdentity);
            throw e;
        }
    }

    private void validateTaskBelongsToUser(TaskIdentity taskIdentity) {
        log.info("Validating task belongs to user for taskIdentity: {}", taskIdentity);

        if (!taskService.isTasksBelongsToUser(taskIdentity)) {
            log.error("Task (id: {}) does not belong to logged user (login: {}).", taskIdentity.getProjectId(), taskIdentity.getLogin());
            throw new TaskBelongsToAnotherUserException("Task with id: " + taskIdentity.getProjectId() + " does not belong to user with login: " + taskIdentity.getLogin());
        }
    }
}
