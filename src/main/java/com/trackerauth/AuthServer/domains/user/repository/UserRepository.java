package com.trackerauth.AuthServer.domains.user.repository;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface UserRepository {

    Optional<UserEntity> findByUsername(@NotNull String name);

    UserEntity save(@NotNull UserEntity entity);
}
