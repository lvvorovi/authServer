package trackerAuth.user.repository;

import trackerAuth.user.domain.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByUsername(String name);

    UserEntity save(UserEntity entity);

    Optional<UserEntity> findById(String id);

    void deleteById(String id);

    boolean existsByUsername(String username);

    boolean existsById(String id);
}
