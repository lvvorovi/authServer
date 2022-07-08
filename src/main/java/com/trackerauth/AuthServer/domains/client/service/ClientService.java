package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

public interface ClientService {

    ClientDtoResponse findById(String clientId);

    ClientDtoResponse save(ClientDtoCreateRequest dto);

    ClientDtoResponse update(ClientDtoUpdateRequest dto);

    void deleteById(String id);

    ClientDtoResponse findByName(String name);
}
