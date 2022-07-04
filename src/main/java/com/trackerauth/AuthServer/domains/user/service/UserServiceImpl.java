package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.CreateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.mapper.UserMapper;
import com.trackerauth.AuthServer.domains.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto findByUserName(String username) {
        assert username != null : "Username must not be null";
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User with username " + username + " was not found"));
        return mapper.entityToDto(entity);
    }

    @Override
    public UserResponseDto save(CreateUserDto dto) {
        assert dto != null : "CreateUserDto must not be null";
        UserEntity entityToSave = mapper.createDtoToEntity(dto);
        entityToSave.setId(UUID.randomUUID());
        entityToSave.setPassword(passwordEncoder.encode(entityToSave.getPassword()));
        UserEntity savedEntity = userRepository.save(entityToSave);
        return mapper.entityToDto(savedEntity);
    }

    @Override
    public UserResponseDto findById(String id) {
        assert id != null : "id must not be null";
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User with username " + id + " was not found"));
        return mapper.entityToDto(entity);
    }
}
