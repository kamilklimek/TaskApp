package com.java.ee.task.controller;

import com.java.ee.task.organizer.TaskFacade;
import com.java.ee.task.organizer.entity.Task;
import com.java.ee.task.organizer.exception.TaskValidationException;
import com.java.ee.task.organizer.identity.TaskIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


@Controller
@Slf4j
@RequestMapping("/project/{projectId}/task")
public class TaskController {
    private final TaskFacade taskFacade;


    public TaskController(TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateTaskView(Model model, @PathVariable Long projectId) {
        model.addAttribute("task", new Task());
        model.addAttribute("projectId", projectId);

        return "task/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createTask(@PathVariable Long projectId, Principal principal, Task task, Model model) {
        final TaskIdentity taskIdentity = new TaskIdentity(principal.getName(), projectId);

        try {
            taskFacade.addTask(taskIdentity, task);
        } catch (TaskValidationException e) {
            log.error("Caught task validation exception while trying to create task: {}, for taskIdenity: {}, due to: {}", task, taskIdentity, e.getMessage());

            model.addAttribute("errors", e.getMessage());
            return "task/create";
        }

        return "redirect:/project/" + projectId;
    }

    @RequestMapping(value = "/{taskId}/edit", method = RequestMethod.GET)
    public String getEditTaskView(Model model, @PathVariable Long projectId, @PathVariable Long taskId, Principal principal) {
        final TaskIdentity taskIdentity = new TaskIdentity(principal.getName(), projectId, taskId);

        Task task = taskFacade.getTask(taskIdentity);

        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);

        return "task/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateTask(Principal principal, Task task, @PathVariable Long projectId, Model model) {
        final TaskIdentity taskIdentity = new TaskIdentity(principal.getName(), projectId, task.getId());

        try {
            taskFacade.updateTask(taskIdentity, task);
        } catch (TaskValidationException e) {
            log.error("Caught task validation exception while trying to edit task: {}, for taskIdenity: {}, due to: {}", task, taskIdentity, e.getMessage());

            model.addAttribute("errors", e.getMessage());
            return "task/edit";
        }

        return "redirect:/project/" + projectId;
    }


    @RequestMapping(value = "/{taskId}/delete", method = RequestMethod.GET)
    public String getDeleteTaskView(Model model, @PathVariable Long taskId, @PathVariable Long projectId, Principal principal) {
        TaskIdentity taskIdentity = new TaskIdentity(principal.getName(), projectId, taskId);
        Task task = taskFacade.getTask(taskIdentity);

        model.addAttribute("name", task.getName());
        model.addAttribute("id", taskId);

        return "task/delete";
    }

    @RequestMapping(value="/{taskId}/delete", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable Long projectId, @PathVariable Long taskId, Principal principal) {
        TaskIdentity taskIdentity = new TaskIdentity(principal.getName(), projectId, taskId);
        taskFacade.removeTask(taskIdentity);
    }

}
