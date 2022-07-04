package com.trackerauth.AuthServer.domains.user;

import javax.validation.constraints.NotEmpty;

public enum UserScope {
    READ("read"),
    WRITE("write"),
    READ_WRITE("read&write");

    private final String code;

    UserScope(@NotEmpty String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
