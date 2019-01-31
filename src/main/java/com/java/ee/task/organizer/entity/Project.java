package com.java.ee.task.organizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String hexColor;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "project", targetEntity = Task.class)
    private List<Task> tasks;


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hexColor='" + hexColor + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    public Project from(Project project) {
        this.name = project.getName();
        this.description = project.description;
        this.hexColor = project.hexColor;
        return this;
    }
}
