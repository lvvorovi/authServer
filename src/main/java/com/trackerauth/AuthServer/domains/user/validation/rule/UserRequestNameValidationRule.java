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
public class UserRequestNameValidationRule implements UserValidationRule {

    private final UserRepository repository;

    public UserRequestNameValidationRule(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(UserDtoCreateRequest dto) {
        Assert.notNull(dto, "UserDtoCreateRequest");
        Assert.notNull(dto.getUsername(), "'username' for User.save()");
        boolean userAlreadyExists = repository.existsByUsername(dto.getUsername());
        if (userAlreadyExists) {
            throw new UserAlreadyExistsException("user with username " + dto.getUsername() + " already exists");
        }
    }

    @Override
    public void validate(UserDtoUpdateRequest dto) {
        Assert.notNull(dto, "UserDtoUpdateRequest");
        Assert.notNull(dto.getUsername(), "'username' for User.update()");

        Optional<UserEntity> optionalEntityById = repository.findById(dto.getId());
        boolean existsById = optionalEntityById.isPresent();
        if (!existsById) {
            throw new UserNotFoundException("User with id " + dto.getId() + " was not found");
        }

        Optional<UserEntity> optionalEntityByUsername = repository.findByUsername(dto.getUsername());
        boolean existsByUsername = optionalEntityByUsername.isPresent();
        if (existsByUsername) {
            boolean equalId = optionalEntityByUsername.get().getId()
                    .equals(dto.getId());
            if (!equalId) throw new UserAlreadyExistsException(
                    "User with username " + dto.getUsername() + " already exists");

        }
    }
}