package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoResponse;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
public interface UserService {

    UserDtoResponse findByUserName(@NotBlank String username);

    UserDtoResponse save(@Valid UserDtoCreateRequest dto);

    UserDtoResponse findById(@NotBlank String id);

    UserDtoResponse update(@Valid UserDtoUpdateRequest dto);

    void deleteById(@NotBlank String id);

}
