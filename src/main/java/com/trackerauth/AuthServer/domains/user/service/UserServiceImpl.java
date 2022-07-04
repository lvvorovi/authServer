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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto findByUserName(String username) {
        assert username != null : "Username shall not be null";
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User with username " + username + " was not found"));
        return userMapper.entityToDto(entity);
    }

    @Override
    public UserResponseDto save(CreateUserDto dto) {
        assert dto != null : "CreateUserDto shall not be null";
        UserEntity entityToSave = userMapper.createDtoToEntity(dto);
        entityToSave.setId(UUID.randomUUID());
        entityToSave.setPassword(passwordEncoder.encode(entityToSave.getPassword()));
        UserEntity savedEntity = userRepository.save(entityToSave);
        return userMapper.entityToDto(savedEntity);
    }
}
