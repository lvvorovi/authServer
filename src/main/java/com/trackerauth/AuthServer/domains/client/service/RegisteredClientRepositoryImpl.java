package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.RegisteredClientImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
