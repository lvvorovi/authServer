package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.exception.UserAlreadyExistsException;
import com.trackerauth.AuthServer.domains.user.exception.UserNotFoundException;
import com.trackerauth.AuthServer.domains.user.mapper.UserMapper;
import com.trackerauth.AuthServer.domains.user.repository.UserRepository;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import org.modelmapper.internal.util.Assert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto findByUserName(String username) {
        UserEntity entity = repository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with username " + username + " was not found"));
        return mapper.entityToResponseDto(entity);
    }

    @Override
    public UserResponseDto save(UserDtoCreateRequest dto) {
        validate(dto);
        UserEntity entityToSave = mapper.createRequestDtoToEntity(dto);
        entityToSave.setId(UUID.randomUUID().toString());
        entityToSave.setScope(UserScope.READ);
        entityToSave.setPassword(passwordEncoder.encode(entityToSave.getPassword()));
        UserEntity savedEntity = repository.save(entityToSave);
        return mapper.entityToResponseDto(savedEntity);
    }

    @Override
    public UserResponseDto findById(String id) {
        Assert.notNull(id, "'id' for User findById()");
        UserEntity entity = repository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with username " + id + " was not found"));
        return mapper.entityToResponseDto(entity);
    }

    @Override
    public UserResponseDto update(UserDtoUpdateRequest userDtoUpdateRequest) {
        validate(userDtoUpdateRequest);
        Assert.notNull(userDtoUpdateRequest, "UserDtoUpdateRequest");
        UserEntity updateRequestEntity = mapper.updateRequestDtoToEntity(userDtoUpdateRequest);
        UserEntity updatedEntity = repository.save(updateRequestEntity);
        return mapper.entityToResponseDto(updatedEntity);
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "'id' for User deleteById()");
        repository.deleteById(id);
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
//        Assert.notNull(dto, "UserDtoUpdateRequest");
//        Assert.notNull(dto.getUsername(), "'username' for User save()");
//        Assert.notNull(dto.getId(), "'id' for User save()");
        boolean existsById = repository.existsById(dto.getId());
        if (!existsById)
            throw new UserNotFoundException("User with id " + dto.getId() + " was not found");
        Optional<UserEntity> optionalEntity = repository.findByUsername(dto.getUsername());
        if (optionalEntity.isPresent()) {
            boolean isEqualId = dto.getId().equals(optionalEntity.get().getId());
            boolean isEqualUsername = dto.getUsername().equals(optionalEntity.get().getUsername());
            if (isEqualUsername && !isEqualId)
                throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists");
        }

    }
}
