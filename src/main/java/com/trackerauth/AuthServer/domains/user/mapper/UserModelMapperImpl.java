package com.trackerauth.AuthServer.domains.user.mapper;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.CreateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UpdateUserDto;
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
    public UserEntity createDtoToEntity(CreateUserDto dto) {
        return mapper.map(dto, UserEntity.class);
    }

    @Override
    public UserEntity updateDtoToEntity(UpdateUserDto dto) {
        return mapper.map(dto, UserEntity.class);
    }

    @Override
    public UserResponseDto entityToDto(UserEntity entity) {
        return mapper.map(entity, UserResponseDto.class);
    }
}
