package com.trackerauth.AuthServer.domains.user.repository;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryImpl extends JpaRepository<UserEntity, UUID>, UserRepository {

    @Override
    Optional<UserEntity> findByUsername(String username);

}
