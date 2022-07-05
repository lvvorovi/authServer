package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateRequestClientDto;
import com.trackerauth.AuthServer.domains.client.dto.UpdateRequestClientDto;
import com.trackerauth.AuthServer.domains.client.mapper.ClientMapper;
import com.trackerauth.AuthServer.domains.client.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClientResponseDto findById(String clientId) {
        assert clientId != null : "ClientId may not be null";
        ClientEntity entity = repository.findById(clientId)
                .orElseThrow(() -> new NoSuchClientException("Client with id " + clientId + " was not found"));
        return mapper.entityToResponseDto(entity);
    }

    @Override
    public ClientResponseDto save(CreateRequestClientDto dto) {
        assert dto != null : "CreateRequestClientDto may not be null";
        ClientEntity requestEntity = mapper.createRequestDtoToEntity(dto);
        requestEntity.setId(UUID.randomUUID().toString());
        requestEntity.setSecret(passwordEncoder.encode(requestEntity.getSecret()));

        ClientEntity savedEntity = repository.save(requestEntity);
        return mapper.entityToResponseDto(savedEntity);
    }

    @Override
    public ClientResponseDto update(UpdateRequestClientDto dto) {
        assert dto != null : "UpdateRequestClientDto may not be null";
        ClientEntity requestEntity = mapper.updateRequestDtoToEntity(dto);
        ClientEntity savedEntity = repository.save(requestEntity);
        return mapper.entityToResponseDto(savedEntity);
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "id shall not be null");
        repository.deleteById(id);
    }
}
