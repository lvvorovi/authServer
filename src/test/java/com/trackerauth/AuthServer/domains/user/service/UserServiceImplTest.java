package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.mapper.UserMapper;
import com.trackerauth.AuthServer.domains.user.repository.UserRepository;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import com.trackerauth.AuthServer.domains.user.validation.UserRequestValidationServiceImpl;
import com.trackerauth.AuthServer.domains.user.validation.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    UserRepository repository;
    @Mock
    UserMapper mapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRequestValidationServiceImpl validationService;

    @InjectMocks
    UserServiceImpl victim;

    private UserEntity newUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId("123456789");
        entity.setUsername("john@email.com");
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        return entity;
    }
    private UserResponseDto newUserResponseDto(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        return dto;
    }
    private UserDtoCreateRequest newUserCreateRequestDto(UserEntity entity) {
        UserDtoCreateRequest createRequestDto = new UserDtoCreateRequest();
        createRequestDto.setPassword(entity.getPassword());
        createRequestDto.setUsername(entity.getUsername());
        return createRequestDto;
    }
    private UserDtoUpdateRequest newUserUpdateRequestDto(UserEntity entity) {
        UserDtoUpdateRequest updateRequestDto = new UserDtoUpdateRequest();
        updateRequestDto.setPassword(entity.getPassword());
        updateRequestDto.setUsername(entity.getUsername());
        updateRequestDto.setId(entity.getId());
        return updateRequestDto;
    }

    @Test
    void findByUserName_returnUserResponseDto() {
        UserEntity entity = newUserEntity();
        UserResponseDto responseDto = newUserResponseDto(entity);
        when(repository.findByUsername(any())).thenReturn(Optional.of(entity));
        when(mapper.entityToDto(any())).thenReturn(responseDto);

        UserResponseDto result = victim.findByUserName("username");

        assertEquals(responseDto, result);
        verify(repository, times(1)).findByUsername(any());
        verify(mapper, times(1)).entityToDto(any());
    }

    @Test
    void findByUserName_throwUserNotFoundException() {
        String username = "username";
        when(repository.findByUsername(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> victim.findByUserName(username))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with username " + username + " was not found");

        verify(repository, times(1)).findByUsername(any());
        verify(mapper, times(0)).entityToDto(any());
    }

    @Test
    void save_returnSavedEntity() {
        UserEntity entityMock = mock(UserEntity.class);
        UserEntity entity = newUserEntity();
        UserDtoCreateRequest createRequest = newUserCreateRequestDto(entity);
        UserResponseDto responseDto = newUserResponseDto(entity);
        doNothing().when(validationService).validate(createRequest);
        when(mapper.dtoToEntity(createRequest)).thenReturn(entityMock);
        when(repository.save(entityMock)).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(responseDto);

        UserResponseDto result = victim.save(createRequest);

        assertEquals(responseDto, result);
        verify(validationService, times(1)).validate(createRequest);
        verify(entityMock, times(1)).setScope(UserScope.READ);
        verify(entityMock, times(1)).setPassword(any());
        verify(entityMock, times(1)).setId(any());
        verify(passwordEncoder, times(1)).encode(any());
        verify(mapper, times(1)).dtoToEntity(createRequest);
        verify(repository, times(1)).save(entityMock);
        verify(mapper, times(1)).entityToDto(entity);
    }

    @Test
    void save_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> victim.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("UserDtoCreateRequest cannot be null");

        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(validationService);
        verifyNoInteractions(mapper);
        verifyNoInteractions(repository);
    }

    @Test
    void findById_returnsUserResponseDto() {
        String id = "id";
        UserEntity entity = newUserEntity();
        UserResponseDto responseDto = newUserResponseDto(entity);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.entityToDto(entity)).thenReturn(responseDto);

        UserResponseDto result = victim.findById(id);

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).entityToDto(entity);
        assertThatNoException().isThrownBy(() -> victim.findById(id));

    }

    @Test
    void findById_throwsUserNotFoundException() {
        String id = "id";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> victim.findById(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with username " + id + " was not found");

        verify(repository, times(1)).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void findById_throwsIllegalArgumentException() {
        String id = null;
        assertThatThrownBy(() -> victim.findById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'id' for User findById() cannot be null");

        verifyNoInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void update_returnsUpdatedDto() {
        UserEntity mockedEntity = mock(UserEntity.class);
        UserEntity entity = newUserEntity();
        UserDtoUpdateRequest updateRequestDto = newUserUpdateRequestDto(entity);
        UserResponseDto responseDto = newUserResponseDto(entity);
        doNothing().when(validationService).validate(updateRequestDto);
        when(mapper.dtoToEntity(updateRequestDto)).thenReturn(mockedEntity);
        when(repository.save(mockedEntity)).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(responseDto);

        UserResponseDto result = victim.update(updateRequestDto);

        assertEquals(responseDto, result);
        verify(validationService, times(1)).validate(updateRequestDto);
        verify(mockedEntity, times(1)).setScope(UserScope.READ);
        verify(mockedEntity, times(1)).setPassword(any());
        verify(passwordEncoder, times(1)).encode(mockedEntity.getPassword());
        verify(mapper, times(1)).dtoToEntity(updateRequestDto);
        verify(repository, times(1)).save(mockedEntity);
        verify(mapper).entityToDto(entity);
    }

    @Test
    void update_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> victim.update(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("UserDtoUpdateRequest cannot be null");

        verifyNoInteractions(validationService);
        verifyNoInteractions(mapper);
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(repository);
    }

    @Test
    void deleteById_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> victim.deleteById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'id' for User deleteById() cannot be null");

        verifyNoInteractions(repository);
    }

    @Test
    void deleteById_delegatesDeletionToRepository() {
        String id = "id";
        doNothing().when(repository).deleteById(id);

        victim.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

}