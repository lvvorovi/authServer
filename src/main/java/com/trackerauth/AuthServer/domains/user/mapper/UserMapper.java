package com.trackerauth.AuthServer.domains.user.mapper;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;

public interface UserMapper {

    UserEntity dtoToEntity(UserDtoCreateRequest dto);

    UserEntity dtoToEntity(UserDtoUpdateRequest dto);

    UserResponseDto entityToDto(UserEntity entity);

}
