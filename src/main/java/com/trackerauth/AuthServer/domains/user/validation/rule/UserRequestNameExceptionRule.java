package com.trackerauth.AuthServer.domains.user.validation.rule;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.repository.UserRepository;
import com.trackerauth.AuthServer.domains.user.validation.exception.UserAlreadyExistsException;
import com.trackerauth.AuthServer.domains.user.validation.exception.UserNotFoundException;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRequestNameExceptionRule implements UserValidationRule {

    private final UserRepository repository;

    public UserRequestNameExceptionRule(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(UserDtoCreateRequest dto) {
        Assert.notNull(dto, "UserDtoCreateRequest");
        Assert.notNull(dto.getUsername(), "'username' for User save()");
        boolean userAlreadyExists = repository.existsByUsername(dto.getUsername());
        if (userAlreadyExists) {
            throw new UserAlreadyExistsException("user with username " + dto.getUsername() + " already exists");
        }

    }

    @Override
    public void validate(UserDtoUpdateRequest dto) {
        boolean existsById = false;
        Optional<UserEntity> optionalEntity = repository.findByUsername(dto.getUsername());
        if (optionalEntity.isPresent()) existsById = true;
        if (!existsById) {
            throw new UserNotFoundException("User with id " + dto.getId() + " was not found");
        }
        boolean requestAndResponseHaveEqualId = dto.getId().equals(optionalEntity.get().getId());
        boolean requestAndResponseHaveEqualUsername = dto.getUsername().equals(optionalEntity.get().getUsername());
        if (requestAndResponseHaveEqualUsername && !requestAndResponseHaveEqualId)
            throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists");
    }
}