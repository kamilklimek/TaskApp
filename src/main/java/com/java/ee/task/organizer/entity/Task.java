package com.java.ee.task.organizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Date scheduleDate;

    @OneToOne
    private Project project;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", scheduleDate=" + scheduleDate +
                '}';
    }

    public Task from(Task task) {
        this.name = task.name;
        this.description = task.description;
        this.scheduleDate = task.scheduleDate;
        return this;
    }
}
