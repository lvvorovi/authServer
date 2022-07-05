package com.trackerauth.AuthServer.domains.user.repository;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryImpl extends CrudRepository<UserEntity, String>, UserRepository {

    @Override
    Optional<UserEntity> findByUsername(String username);

    @Override
    boolean existsByUsername(String username);
}
