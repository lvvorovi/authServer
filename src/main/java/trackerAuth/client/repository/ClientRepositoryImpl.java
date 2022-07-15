package trackerAuth.client.repository;

import trackerAuth.client.domain.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepositoryImpl extends CrudRepository<ClientEntity, String>, ClientRepository {

    @Override
    Optional<ClientEntity> findByClientId(String name);
}
