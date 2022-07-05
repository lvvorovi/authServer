package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateRequestClientDto;
import com.trackerauth.AuthServer.domains.client.dto.UpdateRequestClientDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ClientService {

    ClientResponseDto findById(@NotNull String clientId);

    ClientResponseDto save(@NotNull CreateRequestClientDto dto);

    ClientResponseDto update(UpdateRequestClientDto dto);

    void deleteById(String id);
}
