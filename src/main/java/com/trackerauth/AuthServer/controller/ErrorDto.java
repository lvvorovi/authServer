package com.trackerauth.AuthServer.controller;


import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@Data
public class ErrorDto {

    private String message;

    public ErrorDto(@NotEmpty String message) {
        this.message = message;
    }

}
