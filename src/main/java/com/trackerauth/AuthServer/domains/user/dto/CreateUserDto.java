package com.trackerauth.AuthServer.domains.user.dto;

import com.trackerauth.AuthServer.domains.user.UserScope;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Validated
@Data
public class CreateUserDto {

    @Email
    private String username;
    @NotEmpty
    private String password;
    @NotNull
    private UserScope scope;

}
