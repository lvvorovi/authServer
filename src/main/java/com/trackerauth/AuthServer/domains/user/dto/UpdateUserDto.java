package com.trackerauth.AuthServer.domains.user.dto;

import com.trackerauth.AuthServer.domains.user.UserScope;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Validated
@Data
public class UpdateUserDto {

    @NotEmpty
    private UUID id;
    @Email
    private String username;
    @NotEmpty
    private String password;
    @NotNull
    private UserScope scope;
}
