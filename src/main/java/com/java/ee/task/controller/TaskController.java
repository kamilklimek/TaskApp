package com.java.ee.task.controller;

import com.java.ee.task.organizer.TaskFacade;
import com.java.ee.task.organizer.entity.Task;
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
    public String createTask(@PathVariable Long projectId, Principal principal, Task task) {
        final TaskIdentity taskIdentity = new TaskIdentity(principal.getName(), projectId);

        taskFacade.addTask(taskIdentity, task);

        return "redirect:/project/" + projectId;
    }


    @RequestMapping(value = "/{taskId}/delete", method = RequestMethod.GET)
    public String getDeleteTaskView(Model model, @PathVariable Long taskId, @PathVariable Long projectId, Principal principal) {
        TaskIdentity taskIdentity = new TaskIdentity(principal.getName(), projectId, taskId);
        Task task = taskFacade.getTaskId(taskIdentity);

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
