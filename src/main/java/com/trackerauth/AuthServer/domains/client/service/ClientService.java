package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateClientDto;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.validation.constraints.NotNull;

public interface ClientService {

    ClientResponseDto loadClientByClientId(String clientId);

    ClientResponseDto save(CreateClientDto dto);
}
