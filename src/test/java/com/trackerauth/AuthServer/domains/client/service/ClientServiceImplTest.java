package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.mapper.ClientMapper;
import com.trackerauth.AuthServer.domains.client.repository.ClientRepository;
import com.trackerauth.AuthServer.domains.client.validation.exception.ClientNotFoundException;
import com.trackerauth.AuthServer.domains.client.validation.service.ClientValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientServiceImplTest {

    @Mock
    ClientRepository repository;
    @Mock
    ClientMapper mapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    ClientValidationService validationService;

    @InjectMocks
    ClientServiceImpl victim;

    private ClientEntity newClientEntity() {
        ClientEntity entity = new ClientEntity();
        entity.setId("clientId");
        entity.setSecret("clientSecret");
        entity.setName("clientName");
        return entity;
    }

    private ClientDtoUpdateRequest newClientDtoUpdateRequest(ClientEntity entity) {
        ClientDtoUpdateRequest updateRequest = new ClientDtoUpdateRequest();
        updateRequest.setId(entity.getId());
        updateRequest.setName(entity.getName());
        updateRequest.setSecret(entity.getSecret());
        return updateRequest;
    }

    private ClientDtoCreateRequest newClientDtoCreateRequest(ClientEntity entity) {
        ClientDtoCreateRequest createRequest = new ClientDtoCreateRequest();
        createRequest.setName(entity.getName());
        createRequest.setSecret(entity.getSecret());
        return createRequest;
    }

    private ClientDtoResponse newClientDtoResponse(ClientEntity entity) {
        ClientDtoResponse responseDto = new ClientDtoResponse();
        responseDto.setId(entity.getId());
        responseDto.setName(entity.getName());
        responseDto.setSecret(entity.getSecret());
        return responseDto;
    }

    @Test
    void findById_throwsIllegalArgumentException() {
        String id = null;
        assertThatThrownBy(() -> victim.findById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'clientId' passed to " + victim.getClass() + " findById() cannot be null");

        verifyNoMoreInteractions(repository, mapper, passwordEncoder, validationService);
    }

    @Test
    void findById_throwsNoSuchClientException() {
        String id = "id";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> victim.findById(id))
                .isInstanceOf(NoSuchClientException.class)
                .hasMessage("Client with id '" + id + "' was not found");

        verify(repository, times(1)).findById(id);
        verifyNoInteractions(mapper, passwordEncoder, validationService);
    }

    @Test
    void findById_returnsClientResponseDto() {
        ClientEntity entity = newClientEntity();
        ClientDtoResponse responseDto = newClientDtoResponse(entity);
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(mapper.entityToResponseDto(entity)).thenReturn(responseDto);

        ClientDtoResponse result = victim.findById(entity.getId());

        assertEquals(responseDto, result);
        verify(repository, times(1)).findById(entity.getId());
        verify(mapper, times(1)).entityToResponseDto(entity);
        verifyNoInteractions(passwordEncoder, validationService);
    }

    @Test
    void save_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> victim.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'ClientDtoCreateRequest' passed to " + victim.getClass() + " save() cannot be null");

        verifyNoInteractions(repository, mapper, passwordEncoder, validationService);
    }

    @Test
    void save_returnsClientDtoResponse() {
        String encodedPass = "encodedPass";
        ClientEntity mockedEntity = mock(ClientEntity.class);
        ClientEntity entity = newClientEntity();
        ClientDtoCreateRequest createRequestDto = newClientDtoCreateRequest(entity);
        ClientDtoResponse responseDto = newClientDtoResponse(entity);
        doNothing().when(validationService).validate(createRequestDto);
        when(mapper.createRequestDtoToEntity(createRequestDto)).thenReturn(mockedEntity);
        when(passwordEncoder.encode(mockedEntity.getSecret())).thenReturn(encodedPass);
        when(repository.save(mockedEntity)).thenReturn(entity);
        when(mapper.entityToResponseDto(entity)).thenReturn(responseDto);

        ClientDtoResponse result = victim.save(createRequestDto);

        assertEquals(responseDto, result);
        verify(validationService, times(1)).validate(createRequestDto);
        verify(mapper, times(1)).createRequestDtoToEntity(createRequestDto);
        verify(mockedEntity, times(1)).setId(any());
        verify(mockedEntity, times(1)).setSecret(encodedPass);
        verify(passwordEncoder, times(1)).encode(mockedEntity.getSecret());
        verify(repository, times(1)).save(mockedEntity);
        verify(mapper, times(1)).entityToResponseDto(entity);
    }

    @Test
    void update_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> victim.update(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'ClientDtoUpdateRequest' passed to " +
                        victim.getClass() + " update() cannot be null");

        verifyNoInteractions(mapper, repository, validationService, passwordEncoder);
    }

    @Test
    void update_returnsClientDtoResponse() {
        ClientEntity mockedEntity = mock(ClientEntity.class);
        String encodedSecret = "encodedSecret";
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest updateRequestDto = newClientDtoUpdateRequest(entity);
        ClientDtoResponse responseDto = newClientDtoResponse(entity);
        doNothing().when(validationService).validate(updateRequestDto);
        when(mapper.updateRequestDtoToEntity(updateRequestDto)).thenReturn(mockedEntity);
        when(passwordEncoder.encode(mockedEntity.getSecret())).thenReturn(encodedSecret);
        when(repository.save(mockedEntity)).thenReturn(entity);
        when(mapper.entityToResponseDto(entity)).thenReturn(responseDto);

        ClientDtoResponse result = victim.update(updateRequestDto);

        assertEquals(responseDto, responseDto);
        verify(validationService, times(1)).validate(updateRequestDto);
        verify(mapper, times(1)).updateRequestDtoToEntity(updateRequestDto);
        verify(mockedEntity, times(1)).setSecret(encodedSecret);
        verify(passwordEncoder, times(1)).encode(mockedEntity.getSecret());
        verify(repository, times(1)).save(mockedEntity);
        verify(mapper, times(1)).entityToResponseDto(entity);
    }

    @Test
    void deleteById_throwsIllegalArgumentException() {
        String id = null;
        assertThatThrownBy(() -> victim.deleteById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'id' passed to " + victim.getClass() + " deleteById() cannot be null");

        verifyNoInteractions(repository, mapper, passwordEncoder, validationService);
    }

    @Test
    void deleteById_throwsClientNotFoundException() {
        String id = "id";
        when(repository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> victim.deleteById(id))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessage("Client with id " + id + " was not found");

        verify(repository, times(1)).existsById(id);
        verifyNoInteractions(mapper, passwordEncoder, validationService);
    }

    @Test
    void deleteById_delegatesToRepoToDelete() {
        String id = "id";
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        assertThatNoException().isThrownBy(() -> victim.deleteById(id));

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
        verifyNoInteractions(mapper, passwordEncoder, validationService);
    }

}