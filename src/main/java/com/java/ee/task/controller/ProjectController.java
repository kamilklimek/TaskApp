package com.java.ee.task.controller;

import com.java.ee.task.organizer.ProjectFacade;
import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.exception.ProjectValidationException;
import com.java.ee.task.organizer.identity.ProjectIdentity;
import com.java.ee.task.organizer.identity.UserIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


@Controller
@Slf4j
@RequestMapping("/project")
public class ProjectController {
    private final ProjectFacade projectFacade;

    public ProjectController(ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCurrentUserProjects(Principal principal, Model model) {
        final UserIdentity userIdentity = new UserIdentity(principal.getName());

        model.addAttribute("projects", projectFacade.getCurrentUserProjects(userIdentity));

        return "project/projects";
    }

    @RequestMapping(value="/{projectId}")
    public String getProjectById(Principal principal, @PathVariable Long projectId, Model model) {
        final ProjectIdentity projectIdentity = new ProjectIdentity(principal.getName(), projectId);

        model.addAttribute("project", projectFacade.getProject(projectIdentity));

        return "project/project";
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateProjectView(Model model) {

        model.addAttribute("project", new Project());
        return "project/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProject(Principal principal, Project project, Model model) {
        final ProjectIdentity projectIdentity = new ProjectIdentity(principal.getName());

        try {
            projectFacade.addProject(projectIdentity, project);
        } catch (ProjectValidationException e) {
            log.error("Caught project validation exception while trying to create project: {}, for projectIdentity: {}", project, projectIdentity);

            model.addAttribute("errors", e.getMessage());
            return "project/create";
        } catch (Exception e) {
            log.error("Unrecognized error while creating project: {}, for projectIdentity: {}", project, projectIdentity);
            throw e;
        }

        return "redirect:/project/";
    }


    @RequestMapping(value = "/{projectId}/edit", method = RequestMethod.GET)
    public String getEditProjectView(Model model, @PathVariable Long projectId, Principal principal) {
        ProjectIdentity projectIdentity = new ProjectIdentity(principal.getName(), projectId);
        Project project = projectFacade.getProject(projectIdentity);

        model.addAttribute("project", project);

        return "project/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProject(Principal principal, Project project, Model model) {
        final ProjectIdentity projectIdentity = new ProjectIdentity(principal.getName(), project.getId());

        try {
            projectFacade.updateProject(projectIdentity, project);
        } catch (ProjectValidationException e) {
            log.error("Caught project validation exception while trying to edit project: {}, for projectIdentity: {}", project, projectIdentity);

            model.addAttribute("errors", e.getMessage());
            return "project/edit";
        } catch (Exception e) {
            log.error("Unrecognized error while edit project: {}, for projectIdentity: {}", project, projectIdentity);
            throw e;
        }

        return "redirect:/project/";
    }

    @RequestMapping(value = "/{projectId}/delete", method = RequestMethod.GET)
    public String getDeleteProjectView(Model model, @PathVariable Long projectId, Principal principal) {
        Project project = projectFacade.getProject(new ProjectIdentity(principal.getName(), projectId));

        model.addAttribute("name", project.getName());
        model.addAttribute("id", projectId);

        return "project/delete";
    }

    @RequestMapping(value="/{projectId}/delete", method = RequestMethod.DELETE)
    public void deleteProject(@PathVariable Long projectId, Principal principal) {
        ProjectIdentity projectIdentity = new ProjectIdentity(principal.getName(), projectId);
        projectFacade.removeProject(projectIdentity);
    }

}
