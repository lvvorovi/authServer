package com.trackerauth.AuthServer.domains.user.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class UserAlreadyExistsException extends BadCredentialsException {

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
