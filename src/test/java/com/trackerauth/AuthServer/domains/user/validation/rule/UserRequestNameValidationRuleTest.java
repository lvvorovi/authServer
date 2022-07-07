package com.trackerauth.AuthServer.domains.user.validation.rule;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.repository.UserRepository;
import com.trackerauth.AuthServer.domains.user.repository.UserRepositoryImpl;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import com.trackerauth.AuthServer.domains.user.validation.exception.UserAlreadyExistsException;
import com.trackerauth.AuthServer.domains.user.validation.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserRequestNameValidationRuleTest {

    @Mock
    UserRepositoryImpl repository;

    @InjectMocks
    UserRequestNameValidationRule victim;

    private UserEntity newUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId("123456789");
        entity.setUsername("john@email.com");
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        return entity;
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
    void validateCreateRequest_throwsIllegalArgumentException_onDtoNull() {
        UserDtoCreateRequest createRequestNull = null;
        assertThatThrownBy(() -> victim.validate(createRequestNull))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("UserDtoCreateRequest cannot be null");

        verifyNoInteractions(repository);
    }

    @Test
    void validateCreateRequest_throwsIllegalArgumentException_onUsernameNull() {
        UserDtoCreateRequest createRequest = new UserDtoCreateRequest();
        assertThatThrownBy(() -> victim.validate(createRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'username' for User.save() cannot be null");

        verifyNoInteractions(repository);
    }

    @Test
    void validateCreateRequest_throwsUserAlreadyExistsException() {
        String username = "username@email.com";
        UserDtoCreateRequest request = newUserCreateRequestDto(newUserEntity());
        request.setUsername(username);
        when(repository.existsByUsername(username)).thenReturn(true);

        assertThatThrownBy(() -> victim.validate(request))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("user with username " + username + " already exists");
        verify(repository, times(1)).existsByUsername(username);
    }

    @Test
    void validateCreateRequest_validates() {
        String username = "username@email.com";
        UserDtoCreateRequest request = newUserCreateRequestDto(newUserEntity());
        request.setUsername(username);
        when(repository.existsByUsername(username)).thenReturn(false);

        assertThatNoException().isThrownBy(() -> victim.validate(request));
        verify(repository, times(1)).existsByUsername(username);
    }

    @Test
    void validateUpdateRequest_throwsIllegalArgumentException_onDtoNull() {
        UserDtoUpdateRequest updateRequest = null;
        assertThatThrownBy(() -> victim.validate(updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("UserDtoUpdateRequest cannot be null");

        verifyNoInteractions(repository);
    }

    @Test
    void validateUpdateRequest_throwsIllegalArgumentException_onUsernameNull() {
        UserDtoUpdateRequest createRequest = new UserDtoUpdateRequest();
        assertThatThrownBy(() -> victim.validate(createRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'username' for User.update() cannot be null");

        verifyNoInteractions(repository);
    }

    @Test
    void validateUpdateRequest_throwsUserNotFoundException() {
        UserDtoUpdateRequest updateRequest = newUserUpdateRequestDto(newUserEntity());
        when(repository.findByUsername(updateRequest.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> victim.validate(updateRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id " + updateRequest.getId() + " was not found");
        verify(repository, times(1)).findById(updateRequest.getId());
        verify(repository, times(0)).findByUsername(any());
    }

    @Test
    void validateUpdateRequest_throwsUserAlreadyExistsException() {
        String anotherId = "anotherId";
        UserEntity entity = newUserEntity();
        UserDtoUpdateRequest request = newUserUpdateRequestDto(entity);
        request.setId(anotherId);
        when(repository.findById(request.getId())).thenReturn(Optional.of(entity));
        when(repository.findByUsername(request.getUsername())).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> victim.validate(request))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User with username " + request.getUsername() + " already exists");
        verify(repository, times(1)).findById(request.getId());
        verify(repository, times(1)).findByUsername(request.getUsername());
    }

    @Test
    void validateUpdateRequest_validates() {
        UserEntity entity = newUserEntity();
        UserDtoUpdateRequest request = newUserUpdateRequestDto(entity);
        when(repository.findById(request.getId())).thenReturn(Optional.of(entity));
        when(repository.findByUsername(request.getUsername())).thenReturn(Optional.of(entity));

        assertThatNoException().isThrownBy(() -> victim.validate(request));
        verify(repository, times(1)).findById(request.getId());
        verify(repository, times(1)).findByUsername(request.getUsername());
    }

}