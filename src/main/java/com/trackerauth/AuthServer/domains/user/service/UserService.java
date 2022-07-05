package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

//@Validated
public interface UserService {

    UserResponseDto findByUserName(@NotBlank String username);

    UserResponseDto save(@Valid UserDtoCreateRequest dto);

    UserResponseDto findById(@NotBlank String id);

    UserResponseDto update(@Valid UserDtoUpdateRequest dto);

    void deleteById(@NotBlank String id);

    void validate(UserDtoCreateRequest dto);

    void validate(UserDtoUpdateRequest dto);
}
