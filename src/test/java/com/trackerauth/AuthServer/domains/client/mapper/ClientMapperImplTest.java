package com.trackerauth.AuthServer.domains.client.mapper;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ClientMapperImplTest {

    @Autowired
    ClientMapperImpl victim;


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
    void createRequestDtoToEntity_maps() {
        ClientEntity entity = newClientEntity();
        ClientDtoCreateRequest createRequest = newClientDtoCreateRequest(entity);

        ClientEntity result = victim.createRequestDtoToEntity(createRequest);

        assertNull(result.getId());
        assertEquals(createRequest.getSecret(), result.getSecret());
        assertEquals(createRequest.getName(), result.getName());
    }

    @Test
    void updateRequestDtoToEntity_maps() {
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest updateRequest = newClientDtoUpdateRequest(entity);

        ClientEntity result = victim.updateRequestDtoToEntity(updateRequest);

        assertEquals(updateRequest.getId(), result.getId());
        assertEquals(updateRequest.getSecret(), result.getSecret());
        assertEquals(updateRequest.getName(), result.getName());
    }

    @Test
    void entityToResponseDto_maps() {
        ClientEntity entity = newClientEntity();
        ClientDtoResponse response = newClientDtoResponse(entity);

        ClientDtoResponse result = victim.entityToResponseDto(entity);

        assertEquals(response.getId(), result.getId());
        assertEquals(response.getSecret(), result.getSecret());
        assertEquals(response.getName(), result.getName());
    }
}