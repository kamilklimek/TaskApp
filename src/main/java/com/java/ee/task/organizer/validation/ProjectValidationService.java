package com.java.ee.task.organizer.validation;

import com.java.ee.task.organizer.entity.Project;
import com.java.ee.task.organizer.exception.ProjectValidationException;
import com.java.ee.task.organizer.identity.ProjectIdentity;
import com.java.ee.task.organizer.validation.projectValidators.ProjectValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class ProjectValidationService {
    private final List<ProjectValidator> projectValidators;


    public void validate(ProjectIdentity projectIdentity, Project project) {
        log.debug("Validating project: {}, project identity: {}", project, projectIdentity);

        final List<String> collect = projectValidators.stream()
                .map(validator -> validator.validateProject(projectIdentity, project))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            log.debug("Project validation has some errors: {}", collect);
            throw new ProjectValidationException(String.join(",", collect));
        }
    }
}
