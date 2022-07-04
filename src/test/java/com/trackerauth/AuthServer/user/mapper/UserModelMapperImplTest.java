package com.trackerauth.AuthServer.user.mapper;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.UserScope;
import com.trackerauth.AuthServer.domains.user.dto.CreateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UpdateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.mapper.UserModelMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserModelMapperImplTest {

    @Autowired
    UserModelMapperImpl victim;

    private UserEntity userEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        entity.setUsername("user@user.com");
        return entity;
    }

    private UserResponseDto userResponseDto(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    private CreateUserDto createUserDto(UserEntity entity) {
        CreateUserDto dto = new CreateUserDto();
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    private UpdateUserDto updateUserDto(UserEntity entity) {
        UpdateUserDto dto = new UpdateUserDto();
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Test
    void createDtoToEntity_returnEntity() {
        CreateUserDto userDto = createUserDto(userEntity());
        UserEntity mappedEntity = victim.createDtoToEntity(userDto);
        assertEquals(userDto.getScope(), mappedEntity.getScope());
        assertEquals(userDto.getPassword(), mappedEntity.getPassword());
        assertEquals(userDto.getUsername(), mappedEntity.getUsername());
        assertNull(mappedEntity.getId());
    }

    @Test
    void updateDtoToEntity_returnsEntity() {
        UserEntity entity = userEntity();
        UserEntity mappedEntity = victim.updateDtoToEntity(updateUserDto(entity));
        assertEquals(entity, mappedEntity);
    }

    @Test
    void entityToDto_returnsDto() {
        UserEntity entity = userEntity();
        UserResponseDto responseDto = userResponseDto(entity);
        UserResponseDto mappedDto = victim.entityToDto(entity);
        assertEquals(responseDto, mappedDto);
    }
}