package com.java.ee.task.organizer.identity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserIdentity implements Identity {
    private final String login;
}
