package com.trackerauth.AuthServer.domains.user.mapper;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.CreateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UpdateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;

public interface UserMapper {

    UserEntity createDtoToEntity(CreateUserDto dto);
    UserEntity updateDtoToEntity(UpdateUserDto dto);
    UserResponseDto entityToDto(UserEntity entity);

}
