package com.trackerauth.AuthServer.domains.client.validation.service;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.validation.rule.ClientExistsOnUpdateValidationRule;
import com.trackerauth.AuthServer.domains.client.validation.rule.ClientValidationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ClientValidationServiceImplTest {

    @Mock
    ClientExistsOnUpdateValidationRule rule1;

    ClientValidationServiceImpl victim;
    List<ClientValidationRule> validationRuleList;

    @BeforeEach
    void setUp() {
        validationRuleList = new ArrayList<>();
        validationRuleList.add(rule1);
        victim = new ClientValidationServiceImpl(validationRuleList);
    }

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
    void validateCreateDto_passesToEachRule() {
        ClientDtoCreateRequest createRequest = newClientCreateRequestDto(newClientEntity());

        victim.validate(createRequest);

        validationRuleList.forEach(rule ->
                verify(rule, times(1)).validate(createRequest));
    }

    @Test
    void validateUpdateDto_passesToEachRule() {
        ClientDtoUpdateRequest updateRequest = newClientUpdateRequestDto(newClientEntity());

        victim.validate(updateRequest);

        validationRuleList.forEach(rule ->
                verify(rule, times(1)).validate(updateRequest));
    }
}