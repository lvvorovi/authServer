package com.trackerauth.AuthServer.domains.client.mapper;

import ch.qos.logback.core.net.server.Client;
import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateClientDto;
import com.trackerauth.AuthServer.domains.client.dto.UpdateClientDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ClientMapper {

    ClientEntity createDtoToEntity(@NotNull CreateClientDto dto);
    ClientEntity updateDtoToEntity(@NotNull UpdateClientDto dto);
    ClientResponseDto entityToDto(@NotNull ClientEntity entity);

}
