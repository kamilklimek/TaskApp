package com.java.ee.task.organizer.dao;

import com.java.ee.task.organizer.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUserLogin(String login);
    boolean existsProjectByIdAndUserLogin(Long projectId, String login);
    boolean existsProjectByNameAndUserLogin(String projectName, String login);
}
