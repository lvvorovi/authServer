package com.trackerauth.AuthServer.security;

import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.service.ClientService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Component;

@Component
public class CustomClientDetailsService implements ClientDetailsService {

    private final ClientService clientService;

    public CustomClientDetailsService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientResponseDto clientResponseDto;
        try {
            clientResponseDto = clientService.loadClientByClientId(clientId);
        } catch (NoSuchClientException e) {
            throw new NoSuchClientException(e.getMessage());
        }
        return new CustomClientDetails(clientResponseDto);
    }
}
