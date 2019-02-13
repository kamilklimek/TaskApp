package com.java.ee.task.config;

public enum EmailValidatorMode {
    ALLOW_LOCAL(true),
    NO_ALLOW_LOCAL(false);

    private boolean mode;

    EmailValidatorMode(boolean b) {
        this.mode = b;
    }

    public boolean getMode() {
        return mode;
    }
}
