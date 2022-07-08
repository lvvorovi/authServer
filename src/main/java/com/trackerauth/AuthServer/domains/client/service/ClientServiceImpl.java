package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.mapper.ClientMapper;
import com.trackerauth.AuthServer.domains.client.repository.ClientRepository;
import com.trackerauth.AuthServer.domains.client.validation.exception.ClientNotFoundException;
import com.trackerauth.AuthServer.domains.client.validation.service.ClientValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final ClientValidationService validationService;

    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper, PasswordEncoder passwordEncoder, ClientValidationService validationService) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }

    @Override
    public ClientDtoResponse findById(String clientId) {
        if (clientId == null) throw new IllegalArgumentException("'clientId' passed to " +
                this.getClass() + " findById() cannot be null");
        ClientEntity entity = repository.findById(clientId)
                .orElseThrow(() -> new NoSuchClientException("Client with id '" +
                        clientId + "' was not found"));
        return mapper.entityToResponseDto(entity);
    }

    @Override
    public ClientDtoResponse save(ClientDtoCreateRequest dto) {
        if (dto == null) throw new IllegalArgumentException("'ClientDtoCreateRequest' passed to " +
                this.getClass() + " save() cannot be null");
        validationService.validate(dto);
        ClientEntity requestEntity = mapper.createRequestDtoToEntity(dto);
        requestEntity.setId(UUID.randomUUID().toString());
        requestEntity.setSecret(passwordEncoder.encode(requestEntity.getSecret()));
        ClientEntity savedEntity = repository.save(requestEntity);
        return mapper.entityToResponseDto(savedEntity);
    }

    @Override
    public ClientDtoResponse update(ClientDtoUpdateRequest dto) {
        if (dto == null) throw new IllegalArgumentException("'ClientDtoUpdateRequest' passed to " +
                this.getClass() + " update() cannot be null");
        validationService.validate(dto);
        ClientEntity entityToSave = mapper.updateRequestDtoToEntity(dto);
        entityToSave.setSecret(passwordEncoder.encode(entityToSave.getSecret()));
        ClientEntity savedEntity = repository.save(entityToSave);
        return mapper.entityToResponseDto(savedEntity);
    }

    @Override
    public void deleteById(String id) {
        if (id == null) throw new IllegalArgumentException("'id' passed to " +
                this.getClass() + " deleteById() cannot be null");
        boolean existsById = repository.existsById(id);
        if (existsById) {
            repository.deleteById(id);
        } else {
            throw new ClientNotFoundException("Client with id " + id + " was not found");
        }
    }
}
