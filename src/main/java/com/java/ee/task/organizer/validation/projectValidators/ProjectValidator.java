package com.java.ee.task.organizer.validation.projectValidators;

import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.identity.ProjectIdentity;

import java.util.List;

public interface ProjectValidator {
    List<String> validateProject(ProjectIdentity projectIdentity, Project project);
}
