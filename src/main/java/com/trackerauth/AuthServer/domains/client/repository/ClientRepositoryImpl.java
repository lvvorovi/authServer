package com.trackerauth.AuthServer.domains.client.repository;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepositoryImpl extends JpaRepository<ClientEntity, UUID>, ClientRepository {
}
