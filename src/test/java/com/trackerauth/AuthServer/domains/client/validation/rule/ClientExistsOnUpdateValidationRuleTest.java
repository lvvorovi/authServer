package com.trackerauth.AuthServer.domains.client.validation.rule;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.repository.ClientRepository;
import com.trackerauth.AuthServer.domains.client.validation.exception.ClientNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientExistsOnUpdateValidationRuleTest {

    @Mock
    ClientRepository repository;

    @InjectMocks
    ClientExistsOnUpdateValidationRule victim;

    private ClientEntity newClientEntity() {
        ClientEntity entity = new ClientEntity();
        entity.setId("clientId");
        entity.setSecret("clientSecret");
        entity.setName("clientName");
        return entity;
    }

    private ClientDtoUpdateRequest newClientUpdateRequestDto(ClientEntity entity) {
        ClientDtoUpdateRequest updateRequest = new ClientDtoUpdateRequest();
        updateRequest.setId(entity.getId());
        updateRequest.setName(entity.getName());
        updateRequest.setSecret(entity.getSecret());
        return updateRequest;
    }

    private ClientDtoCreateRequest newClientCreateRequestDto(ClientEntity entity) {
        ClientDtoCreateRequest createRequest = new ClientDtoCreateRequest();
        createRequest.setName(entity.getName());
        createRequest.setSecret(entity.getSecret());
        return createRequest;
    }


    @Test
    void validateCreateRequest_doNothing() {
        ClientDtoCreateRequest createRequestMock = mock(ClientDtoCreateRequest.class);
        victim.validate(createRequestMock);

        verifyNoInteractions(repository, createRequestMock);
    }

    @Test
    void validateUpdateRequest_throwsClientNotFoundException() {
        ClientDtoUpdateRequest updateRequest = newClientUpdateRequestDto(newClientEntity());
        when(repository.existsById(updateRequest.getId())).thenReturn(false);

        assertThatThrownBy(() -> victim.validate(updateRequest))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessage("Client with 'id' " + updateRequest.getId() + " was not found");
        verify(repository, times(1)).existsById(updateRequest.getId());
    }

    @Test
    void validateUpdateRequest_validates() {
        ClientDtoUpdateRequest updateRequest = newClientUpdateRequestDto(newClientEntity());
        when(repository.existsById(updateRequest.getId())).thenReturn(true);

        assertThatNoException().isThrownBy(() -> victim.validate(updateRequest));
        verify(repository, times(1)).existsById(updateRequest.getId());
    }
}