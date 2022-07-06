package com.trackerauth.AuthServer.domains.user.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
@Data
public class UserDtoUpdateRequest {

    @NotBlank
    private String id;
    @Email
    @NotNull
    private String username;
    @NotBlank
    @Size(min = 3, max = 30, message = "length 3 to 30")
    private String password;

}
