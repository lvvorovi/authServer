package com.trackerauth.AuthServer.domains.client.validation.exception;

public class NoSuchClientException extends RuntimeException {

    public NoSuchClientException(String message) {
        super(message);
    }
}
