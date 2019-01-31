package com.java.ee.task.organizer.identity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TaskIdentity extends ProjectIdentity implements Identity {
    private Long taskId;

    public TaskIdentity(String login, Long projectId) {
        super(login, projectId);
    }

    public TaskIdentity(String login, Long projectId, Long taskId) {
        super(login, projectId);

        this.taskId = taskId;
    }
}
