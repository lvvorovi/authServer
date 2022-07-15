package trackerAuth.client.validation.rule;

import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoUpdateRequest;
import trackerAuth.client.repository.ClientRepository;
import trackerAuth.client.validation.exception.ClientNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ClientExistsOnUpdateValidationRule implements ClientValidationRule {

    private final ClientRepository repository;

    public ClientExistsOnUpdateValidationRule(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(ClientDtoCreateRequest dto) {

    }

    @Override
    public void validate(ClientDtoUpdateRequest dto) {
        boolean exists = repository.existsById(dto.getId());
        if (!exists) throw new ClientNotFoundException("Client with 'id' " +
                dto.getId() + " was not found");
    }
}
