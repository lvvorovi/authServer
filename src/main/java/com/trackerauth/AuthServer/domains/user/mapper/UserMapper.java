package com.trackerauth.AuthServer.domains.user.mapper;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoResponse;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;

public interface UserMapper {

    UserEntity dtoToEntity(UserDtoCreateRequest dto);

    UserEntity dtoToEntity(UserDtoUpdateRequest dto);

    UserDtoResponse entityToDto(UserEntity entity);

}
