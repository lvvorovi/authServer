package com.trackerauth.AuthServer.domains.client.validation.rule;

import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.repository.ClientRepository;
import com.trackerauth.AuthServer.domains.client.validation.exception.ClientNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ClientExistsOnUpdateValidationRule implements ClientValidationRule {

    private final ClientRepository repository;

    public ClientExistsOnUpdateValidationRule(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(ClientDtoCreateRequest dto) {

    }

    @Override
    public void validate(ClientDtoUpdateRequest dto) {
        boolean exists = repository.existsById(dto.getId());
        if (!exists) throw new ClientNotFoundException("Client with 'id' " +
                dto.getId() + " was not found");
    }
}
