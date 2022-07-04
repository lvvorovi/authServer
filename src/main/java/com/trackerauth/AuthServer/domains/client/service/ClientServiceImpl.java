package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateClientDto;
import com.trackerauth.AuthServer.domains.client.mapper.ClientMapper;
import com.trackerauth.AuthServer.domains.client.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

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
        return mapper.entityToDto(entity);
    }

    @Override
    public ClientResponseDto save(CreateClientDto dto) {
        assert dto != null : "CreateClientDto may not be null";
        ClientEntity requestEntity = mapper.createDtoToEntity(dto);
        requestEntity.setId(UUID.randomUUID().toString());
        requestEntity.setSecret(passwordEncoder.encode(requestEntity.getSecret()));

        ClientEntity savedEntity = repository.save(requestEntity);
        return mapper.entityToDto(savedEntity);
    }

}
