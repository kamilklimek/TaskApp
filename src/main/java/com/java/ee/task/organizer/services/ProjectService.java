package com.java.ee.task.organizer.services;

import com.java.ee.task.organizer.dao.ProjectRepository;
import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.organizer.exception.ProjectBelongsToAnotherUserException;
import com.java.ee.task.organizer.exception.ProjectCouldNotBeFoundException;
import com.java.ee.task.organizer.identity.Identity;
import com.java.ee.task.organizer.identity.ProjectIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;


    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Long saveProject(Project project) {
        log.info("Save project: {}", project);
        return projectRepository.save(project).getId();
    }

    public List<Project> getUserProjects(Identity user) {
        log.info("Getting projects for user identity: {}", user);

        return projectRepository.findAllByUserLogin(user.getLogin());
    }

    public Project getProject(ProjectIdentity projectIdentity) {
        return projectRepository.findById(projectIdentity.getProjectId())
                .orElseThrow(ProjectCouldNotBeFoundException::new);
    }


    public void validateProjectBelongsToUser(ProjectIdentity projectIdentity) {
        log.info("Validating project belongs to user for projectIdentity: {}", projectIdentity);

        if (!isProjectBelongsToUser(projectIdentity)) {
            log.error("Project (id: {}) does not belong to logged user (login: {}).", projectIdentity.getProjectId(), projectIdentity.getLogin());
            throw new ProjectBelongsToAnotherUserException("Project with id: " + projectIdentity.getProjectId() + " does not belong to user with login: " + projectIdentity.getLogin());
        }
    }

    public boolean isProjectBelongsToUser(ProjectIdentity projectIdentity) {
        return projectRepository.existsProjectByIdAndUserLogin(projectIdentity.getProjectId(), projectIdentity.getLogin());
    }

    public void deleteProject(ProjectIdentity projectIdentity) {
        projectRepository.deleteById(projectIdentity.getProjectId());
    }


    public void updateProject(ProjectIdentity projectIdentity, Project project) {
        Project projectFromDb = getProject(projectIdentity);

        projectFromDb = projectFromDb.from(project);

        projectRepository.save(projectFromDb);
    }

    public boolean isUserHasAlreadyProjectName(ProjectIdentity projectIdentity, Project project) {
        return projectRepository.existsProjectByNameAndUserLogin(project.getName(), projectIdentity.getLogin());
    }

    public boolean isProjectNameHasChanged(ProjectIdentity projectIdentity, Project project) {
        try {
            Project projectFromDb = getProject(projectIdentity);
            return !projectFromDb.getName().equals(project.getName());
        } catch (Exception e) {
            return true;
        }
    }
}
