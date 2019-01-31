package com.java.ee.task.organizer.validation.projectValidators;

import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.identity.ProjectIdentity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LengthProjectNameValidator implements ProjectValidator {
    private final int projectNameMinLength;
    private final int projectNameMaxLength;

    public LengthProjectNameValidator(@Value("${project.name.min.length}") int projectNameMinLength,
                                      @Value("${project.name.max.length}") int projectNameMaxLength) {

        this.projectNameMinLength = projectNameMinLength;
        this.projectNameMaxLength = projectNameMaxLength;
    }


    @Override
    public List<String> validateProject(ProjectIdentity projectIdentity, Project project) {
        if (StringUtils.isEmpty(project.getName())) {
            return Collections.singletonList("Project name cannot be empty.");
        }

        if (StringUtils.length(project.getName()) > projectNameMaxLength) {
            return Collections.singletonList("Project name cannot be longer than " + projectNameMaxLength + " characters.");
        }


        if (StringUtils.length(project.getName()) < projectNameMinLength) {
            return Collections.singletonList("Project name cannot be shorter than " + projectNameMinLength + " characters.");
        }

        return Collections.emptyList();
    }
}
