package trackerAuth.client.service;

import trackerAuth.client.dto.RegisteredClientImpl;
import trackerAuth.client.dto.ClientDtoResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {

    private final ClientService service;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        ClientDtoResponse response = service.findById(id);
        if (response == null) return null;
        return new RegisteredClientImpl(response);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        ClientDtoResponse response = service.findByClientId(clientId);
        if (response == null) return null;
        return new RegisteredClientImpl(response);
    }


}
