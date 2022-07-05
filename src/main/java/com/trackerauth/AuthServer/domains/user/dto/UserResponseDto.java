package com.trackerauth.AuthServer.domains.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponseDto extends RepresentationModel<UserResponseDto> {

    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private UserScope scope;
}
