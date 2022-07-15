package trackerAuth.user.service;

import trackerAuth.user.domain.UserEntity;
import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoResponse;
import trackerAuth.user.dto.UserDtoUpdateRequest;
import trackerAuth.user.mapper.UserMapper;
import trackerAuth.user.repository.UserRepository;
import trackerAuth.user.scope.UserScope;
import trackerAuth.user.validation.UserRequestValidationServiceImpl;
import trackerAuth.user.validation.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    ArgumentCaptor<UserEntity> entityCaptor;

    private UserEntity newUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId("123456789");
        entity.setUsername("john@email.com");
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        return entity;
    }

    private UserDtoResponse newUserDtoResponse(UserEntity entity) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        return dto;
    }

    private UserDtoCreateRequest newUserDtoCreateResponse(UserEntity entity) {
        UserDtoCreateRequest createRequestDto = new UserDtoCreateRequest();
        createRequestDto.setPassword(entity.getPassword());
        createRequestDto.setUsername(entity.getUsername());
        return createRequestDto;
    }

    private UserDtoUpdateRequest newUserDtoUpdateRequest(UserEntity entity) {
        UserDtoUpdateRequest updateRequestDto = new UserDtoUpdateRequest();
        updateRequestDto.setPassword(entity.getPassword());
        updateRequestDto.setUsername(entity.getUsername());
        updateRequestDto.setId(entity.getId());
        return updateRequestDto;
    }

    @Test
    void findByUserName_returnUserResponseDto() {
        UserEntity entity = newUserEntity();
        UserDtoResponse responseDto = newUserDtoResponse(entity);
        when(repository.findByUsername(any())).thenReturn(Optional.of(entity));
        when(mapper.entityToDto(any())).thenReturn(responseDto);

        UserDtoResponse result = victim.findByUserName("username");

        assertEquals(responseDto, result);
        verify(repository, times(1)).findByUsername(any());
        verify(mapper, times(1)).entityToDto(any());
        verifyNoInteractions(validationService, passwordEncoder);
    }

    @Test
    void findByUserName_throwsIllegalArgumentExceptionForNull() {
        String id = null;
        assertThatThrownBy(() -> victim.findById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'id' passed to " + victim.getClass() + " findById() cannot be null");

        verifyNoInteractions(validationService, passwordEncoder, repository, mapper);
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
        verifyNoInteractions(passwordEncoder, validationService);
    }

    @Test
    void save_returnSavedEntity() {
        String encodedPassword = "encodedPassword";
        UserEntity mockedEntity = mock(UserEntity.class);
        UserEntity entity = newUserEntity();
        UserDtoCreateRequest createRequest = newUserDtoCreateResponse(entity);
        UserDtoResponse responseDto = newUserDtoResponse(entity);
        doNothing().when(validationService).validate(createRequest);
        when(mapper.dtoToEntity(createRequest)).thenReturn(mockedEntity);
        when(passwordEncoder.encode(mockedEntity.getPassword())).thenReturn(encodedPassword);
        when(repository.save(mockedEntity)).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(responseDto);

        UserDtoResponse result = victim.save(createRequest);

        assertEquals(responseDto, result);
        verify(validationService, times(1)).validate(createRequest);
        verify(mockedEntity, times(1)).setScope(UserScope.READ);
        verify(mockedEntity, times(1)).setPassword(any());
        verify(mockedEntity, times(1)).setId(any());
        verify(passwordEncoder, times(1)).encode(any());
        verify(mapper, times(1)).dtoToEntity(createRequest);
        verify(repository, times(1)).save(mockedEntity);
        verify(mapper, times(1)).entityToDto(entity);
    }

    @Test
    void save_verifyCorrectEntityPassedToRepository() {
        String encodedPassword = "encodedPassword";
        UserEntity entity = newUserEntity();
        UserDtoCreateRequest createRequest = newUserDtoCreateResponse(entity);
        UserDtoResponse responseDto = newUserDtoResponse(entity);
        doNothing().when(validationService).validate(createRequest);
        when(mapper.dtoToEntity(createRequest)).thenReturn(entity);
        when(passwordEncoder.encode(entity.getPassword())).thenReturn(encodedPassword);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(responseDto);

        UserDtoResponse result = victim.save(createRequest);

        assertEquals(responseDto, result);
        verify(validationService, times(1)).validate(createRequest);
        verify(passwordEncoder, times(1)).encode(any());
        verify(mapper, times(1)).dtoToEntity(createRequest);
        verify(repository, times(1)).save(entityCaptor.capture());
        verify(mapper, times(1)).entityToDto(entity);

        UserEntity capturedEntity = entityCaptor.getValue();
        assertEquals(36, capturedEntity.getId().length());
        assertEquals(UserScope.READ, capturedEntity.getScope());
        assertEquals(encodedPassword, capturedEntity.getPassword());
    }

    @Test
    void save_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> victim.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'UserDtoCreateRequest' passed to " + victim.getClass() + " save() cannot be null");

        verifyNoInteractions(passwordEncoder, validationService, mapper, repository);
    }

    @Test
    void findById_returnsUserResponseDto() {
        String id = "id";
        UserEntity entity = newUserEntity();
        UserDtoResponse responseDto = newUserDtoResponse(entity);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.entityToDto(entity)).thenReturn(responseDto);

        UserDtoResponse result = victim.findById(id);

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).entityToDto(entity);
        assertThatNoException().isThrownBy(() -> victim.findById(id));
        verifyNoInteractions(passwordEncoder, validationService);
    }

    @Test
    void findById_throwsUserNotFoundException() {
        String id = "id";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> victim.findById(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with username " + id + " was not found");

        verify(repository, times(1)).findById(id);
        verifyNoInteractions(mapper, passwordEncoder, validationService);
    }

    @Test
    void findById_throwsIllegalArgumentException() {
        String id = null;
        assertThatThrownBy(() -> victim.findById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'id' passed to " + victim.getClass() + " findById() cannot be null");

        verifyNoInteractions(repository, mapper, passwordEncoder);
    }

    @Test
    void update_returnsUpdatedDto() {
        UserEntity mockedEntity = mock(UserEntity.class);
        UserEntity entity = newUserEntity();
        UserDtoUpdateRequest updateRequestDto = newUserDtoUpdateRequest(entity);
        UserDtoResponse responseDto = newUserDtoResponse(entity);
        doNothing().when(validationService).validate(updateRequestDto);
        when(mapper.dtoToEntity(updateRequestDto)).thenReturn(mockedEntity);
        when(repository.save(mockedEntity)).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(responseDto);

        UserDtoResponse result = victim.update(updateRequestDto);

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
                .hasMessage("'UserDtoUpdateRequest' passed to " + victim.getClass() + " update() cannot be null");

        verifyNoInteractions(validationService);
        verifyNoInteractions(mapper);
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(repository);
    }

    @Test
    void deleteById_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> victim.deleteById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'id' passed to " + victim.getClass() + " deleteById() cannot be null");

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