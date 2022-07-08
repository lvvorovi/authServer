package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.SecurityClientDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityClientDetailsService implements ClientDetailsService {

    private final ClientService clientService;

    public SecurityClientDetailsService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.debug("Requested to loadClientByClientId() with id = {}", clientId);
        ClientDtoResponse clientDtoResponse;
        try {
            clientDtoResponse = clientService.findById(clientId);
        } catch (NoSuchClientException e) {
            throw new ClientRegistrationException("Client with id '" + clientId + "' was not found");
        }
        log.debug("Loaded ClientDtoResponse {}", clientDtoResponse);

        return new SecurityClientDetails(clientDtoResponse);
    }
}
