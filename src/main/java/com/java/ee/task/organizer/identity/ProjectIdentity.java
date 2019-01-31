package com.java.ee.task.organizer.identity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProjectIdentity extends UserIdentity implements Identity {
    private Long projectId;

    public ProjectIdentity(String login) {
        super(login);
    }

    public ProjectIdentity(String login, Long projectId) {
        super(login);
        this.projectId = projectId;
    }
}
