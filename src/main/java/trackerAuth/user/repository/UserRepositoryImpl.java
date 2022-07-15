package trackerAuth.user.repository;

import trackerAuth.user.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryImpl extends CrudRepository<UserEntity, String>, UserRepository {

    @Override
    Optional<UserEntity> findByUsername(String username);

    @Override
    boolean existsByUsername(String username);

    @Override
    Optional<UserEntity> findById(String id);


}
