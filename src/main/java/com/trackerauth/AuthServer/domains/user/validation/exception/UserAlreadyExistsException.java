package com.trackerauth.AuthServer.domains.user.validation.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}