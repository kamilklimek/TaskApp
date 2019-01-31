package com.java.ee.task.organizer.validation.projectValidators;

import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.identity.ProjectIdentity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class LengthProjectDescriptionValidator implements ProjectValidator {
    private final int projectDescriptionMaxLength;

    public LengthProjectDescriptionValidator(@Value("${project.desc.max.length}") int projectDescriptionMaxLength) {
        this.projectDescriptionMaxLength = projectDescriptionMaxLength;
    }

    @Override
    public List<String> validateProject(ProjectIdentity projectIdentity, Project project) {
        if (StringUtils.length(project.getDescription()) > projectDescriptionMaxLength) {
            return Collections.singletonList("Project description cannot be longer than " + projectDescriptionMaxLength + " characters.");
        }

        return Collections.emptyList();
    }
}
