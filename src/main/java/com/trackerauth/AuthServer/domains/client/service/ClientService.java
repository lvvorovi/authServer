package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ClientService {

    ClientDtoResponse findById(@NotNull String clientId);

    ClientDtoResponse save(@NotNull ClientDtoCreateRequest dto);

    ClientDtoResponse update(ClientDtoUpdateRequest dto);

    void deleteById(String id);
}
