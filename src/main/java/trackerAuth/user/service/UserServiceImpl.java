package trackerAuth.user.service;

import trackerAuth.user.repository.UserRepository;
import trackerAuth.user.validation.UserRequestValidationService;
import trackerAuth.user.validation.exception.UserNotFoundException;
import trackerAuth.user.domain.UserEntity;
import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoResponse;
import trackerAuth.user.dto.UserDtoUpdateRequest;
import trackerAuth.user.mapper.UserMapper;
import trackerAuth.user.scope.UserScope;
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
    public UserDtoResponse findByUserName(String username) {
        if (username == null) throw new IllegalArgumentException("'username' passed to " +
                this.getClass() + " findByUserName() cannot be null");
        UserEntity entity = repository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with username " + username + " was not found"));
        return mapper.entityToDto(entity);
    }

    @Override
    public UserDtoResponse save(UserDtoCreateRequest dto) {
        if (dto == null) throw new IllegalArgumentException("'UserDtoCreateRequest' passed to " +
                this.getClass() + " save() cannot be null");
        validationService.validate(dto);
        UserEntity entityToSave = mapper.dtoToEntity(dto);
        entityToSave.setId(UUID.randomUUID().toString());
        entityToSave.setScope(UserScope.READ);
        entityToSave.setPassword(passwordEncoder.encode(entityToSave.getPassword()));
        UserEntity savedEntity = repository.save(entityToSave);
        return mapper.entityToDto(savedEntity);
    }

    @Override
    public UserDtoResponse findById(String id) {
        if (id == null) throw new IllegalArgumentException("'id' passed to " +
                this.getClass() + " findById() cannot be null");
        UserEntity entity = repository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with username " + id + " was not found"));
        return mapper.entityToDto(entity);
    }

    @Override
    public UserDtoResponse update(UserDtoUpdateRequest dto) {
        if (dto == null) throw new IllegalArgumentException("'UserDtoUpdateRequest' passed to " +
                this.getClass() + " update() cannot be null");
        validationService.validate(dto);
        UserEntity entityToUpdate = mapper.dtoToEntity(dto);
        entityToUpdate.setScope(UserScope.READ);
        entityToUpdate.setPassword(passwordEncoder.encode(entityToUpdate.getPassword()));
        UserEntity updatedEntity = repository.save(entityToUpdate);
        return mapper.entityToDto(updatedEntity);
    }

    @Override
    public void deleteById(String id) {
        if (id == null) throw new IllegalArgumentException("'id' passed to " +
                this.getClass() + " deleteById() cannot be null");
        repository.deleteById(id);
    }

}
