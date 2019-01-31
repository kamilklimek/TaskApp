package com.java.ee.task.organizer;

import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.organizer.identity.Identity;
import com.java.ee.task.organizer.identity.ProjectIdentity;
import com.java.ee.task.organizer.services.ProjectService;
import com.java.ee.task.organizer.validation.ProjectValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectFacade {
    private final ProjectService projectService;
    private final UserFacade userFacade;
    private final ProjectValidationService projectValidationService;

    @Autowired
    public ProjectFacade(ProjectService projectService, UserFacade userFacade, ProjectValidationService projectValidationService) {
        this.projectService = projectService;
        this.userFacade = userFacade;
        this.projectValidationService = projectValidationService;
    }

    public List<Project> getCurrentUserProjects(Identity identity) {
        return projectService.getUserProjects(identity);
    }

    public Long addProject(ProjectIdentity projectIdentity, Project project) {
        projectValidationService.validate(projectIdentity, project);

        User user = userFacade.getCurrentLoggedUser(projectIdentity);
        project.setUser(user);

        return projectService.saveProject(project);
    }


    public Project getProject(ProjectIdentity projectIdentity) {
        Project project = projectService.getProject(projectIdentity);

        projectService.validateProjectBelongsToUser(projectIdentity);

        return project;
    }

    public void updateProject(ProjectIdentity projectIdentity, Project project) {
        projectValidationService.validate(projectIdentity, project);

        projectService.validateProjectBelongsToUser(projectIdentity);


        projectService.updateProject(projectIdentity, project);
    }

    public void removeProject(ProjectIdentity projectIdentity) {
        projectService.deleteProject(projectIdentity);
    }
}
