package com.trackerauth.AuthServer.security;

import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class CustomClientDetailsService implements ClientDetailsService {

    private final ClientService clientService;

    public CustomClientDetailsService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info("loadClientByClientId with {}", clientId);
        ClientResponseDto clientResponseDto;
        try {
            clientResponseDto = clientService.findById(clientId);
        } catch (NoSuchClientException e) {
            throw new NoSuchClientException(e.getMessage());
        }
        log.info(Arrays.toString(Thread.currentThread().getStackTrace()));
        log.info("loaded client {}", clientResponseDto);

        return new CustomClientDetails(clientResponseDto);
    }
}
