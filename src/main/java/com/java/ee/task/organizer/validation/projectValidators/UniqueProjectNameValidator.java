package com.java.ee.task.organizer.validation.projectValidators;

import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.identity.ProjectIdentity;
import com.java.ee.task.organizer.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class UniqueProjectNameValidator implements ProjectValidator {
    private final ProjectService projectService;


    @Override
    public List<String> validateProject(ProjectIdentity projectIdentity, Project project) {
        if (!projectService.isProjectNameHasChanged(projectIdentity, project)) {
            return Collections.emptyList();
        }


        if (projectService.isUserHasAlreadyProjectName(projectIdentity, project)) {
            return Collections.singletonList("Project name " + project.getName() + " is already used.");
        }

        return Collections.emptyList();
    }
}
