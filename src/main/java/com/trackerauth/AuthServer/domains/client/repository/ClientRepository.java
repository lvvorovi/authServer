package com.trackerauth.AuthServer.domains.client.repository;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface ClientRepository {

    Optional<ClientEntity> findById(@NotNull String id);

    ClientEntity save(ClientEntity entity);
}
