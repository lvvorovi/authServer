package com.trackerauth.AuthServer.domains.user.mapper;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapperImpl implements UserMapper {

    private final ModelMapper mapper;

    public UserModelMapperImpl(ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    @Override
    public UserEntity dtoToEntity(UserDtoCreateRequest dto) {
        return mapper.map(dto, UserEntity.class);
    }

    @Override
    public UserEntity dtoToEntity(UserDtoUpdateRequest dto) {
        return mapper.map(dto, UserEntity.class);
    }

    @Override
    public UserResponseDto entityToDto(UserEntity entity) {
        return mapper.map(entity, UserResponseDto.class);
    }
}
