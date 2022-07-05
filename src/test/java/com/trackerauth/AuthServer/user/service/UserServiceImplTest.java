package com.trackerauth.AuthServer.user.service;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.mapper.UserMapper;
import com.trackerauth.AuthServer.domains.user.repository.UserRepository;
import com.trackerauth.AuthServer.domains.user.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    UserMapper mapper;
    @Mock
    UserRepository repository;
    @InjectMocks
    UserServiceImpl victim;

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

    @Test
    @DisplayName("findByUserName_throwsExceptionWhenNull")
    void findByUserName_withNull() {
        assertThatThrownBy(() -> victim.findByUserName(null))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Username shall not be null");
        verify(repository, times(0)).findByUsername(any());
        verify(mapper, times(0)).entityToResponseDto(any());
    }

    @Test
    void findByUserName_returnsUserResponseDto() {
        UserEntity userEntity = userEntity();
        UserResponseDto userDto = userResponseDto(userEntity);
        when(repository.findByUsername(any())).thenReturn(Optional.of(userEntity));
        when(mapper.entityToResponseDto(any())).thenReturn(userDto);
        UserResponseDto responseUserDto = victim.findByUserName("user@user.com");
        assertEquals(userDto, responseUserDto);
        verify(repository, times(1)).findByUsername(any());
        verify(mapper, times(1)).entityToResponseDto(any());
    }

    @Test
    void findByUserName_ThrowsException() {
        when(repository.findByUsername(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> victim.findByUserName("user@user.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User with username user@user.com was not found");
        verify(mapper, times(0)).entityToResponseDto(any());

    }


}