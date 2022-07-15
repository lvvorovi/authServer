package trackerAuth.client.repository;

import trackerAuth.client.domain.ClientEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface ClientRepository {

    Optional<ClientEntity> findById(String id);

    Optional<ClientEntity> findByClientId(String name);

    ClientEntity save(ClientEntity entity);

    void deleteById(String id);

    boolean existsById(String id);
}
