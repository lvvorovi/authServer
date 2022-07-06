package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.validation.UserRequestValidationService;
import com.trackerauth.AuthServer.domains.user.validation.exception.UserNotFoundException;
import com.trackerauth.AuthServer.domains.user.mapper.UserMapper;
import com.trackerauth.AuthServer.domains.user.repository.UserRepository;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import org.modelmapper.internal.util.Assert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRequestValidationService validationService;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder, UserRequestValidationService validationService) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }

    @Override
    public UserResponseDto findByUserName(String username) {
        UserEntity entity = repository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with username " + username + " was not found"));
        return mapper.entityToDto(entity);
    }

    @Override
    public UserResponseDto save(UserDtoCreateRequest dto) {
        Assert.notNull(dto, "UserDtoCreateRequest");
        validationService.validate(dto);
        UserEntity entityToSave = mapper.dtoToEntity(dto);
        entityToSave.setId(UUID.randomUUID().toString());
        entityToSave.setScope(UserScope.READ);
        entityToSave.setPassword(passwordEncoder.encode(entityToSave.getPassword()));
        UserEntity savedEntity = repository.save(entityToSave);
        return mapper.entityToDto(savedEntity);
    }

    @Override
    public UserResponseDto findById(String id) {
        Assert.notNull(id, "'id' for User findById()");
        UserEntity entity = repository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with username " + id + " was not found"));
        return mapper.entityToDto(entity);
    }

    @Override
    public UserResponseDto update(UserDtoUpdateRequest dto) {
        Assert.notNull(dto, "UserDtoUpdateRequest");
        validationService.validate(dto);
        UserEntity entityToUpdate = mapper.dtoToEntity(dto);
        entityToUpdate.setScope(UserScope.READ);
        entityToUpdate.setPassword(passwordEncoder.encode(entityToUpdate.getPassword()));
        UserEntity updatedEntity = repository.save(entityToUpdate);
        return mapper.entityToDto(updatedEntity);
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "'id' for User deleteById()");
        repository.deleteById(id);
    }

}
