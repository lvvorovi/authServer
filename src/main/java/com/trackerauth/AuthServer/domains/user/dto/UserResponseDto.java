package com.trackerauth.AuthServer.domains.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackerauth.AuthServer.domains.user.UserScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponseDto extends RepresentationModel<UserResponseDto> {

    private UUID id;
    private String username;
    @JsonIgnore
    private String password;
    private UserScope scope;
}
