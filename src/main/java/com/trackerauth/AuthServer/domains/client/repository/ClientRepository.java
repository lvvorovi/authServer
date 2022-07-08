package com.trackerauth.AuthServer.domains.client.repository;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface ClientRepository {

    Optional<ClientEntity> findById(String id);

    Optional<ClientEntity> findByName(String name);

    ClientEntity save(ClientEntity entity);

    void deleteById(String id);

    boolean existsById(String id);
}
