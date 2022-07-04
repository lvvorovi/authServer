package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.dto.CreateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto findByUserName(String username);

    UserResponseDto save(CreateUserDto dto);
}
