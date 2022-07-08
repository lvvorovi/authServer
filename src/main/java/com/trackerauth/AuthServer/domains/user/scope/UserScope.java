package com.trackerauth.AuthServer.domains.user.scope;

import javax.validation.constraints.NotEmpty;

public enum UserScope {
    READ("READ"),
    WRITE("WRITE"),
    READ_WRITE("READ&WRITE");

    private final String code;

    UserScope(@NotEmpty String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
