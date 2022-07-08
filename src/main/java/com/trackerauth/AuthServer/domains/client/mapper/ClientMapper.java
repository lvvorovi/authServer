package com.trackerauth.AuthServer.domains.client.mapper;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ClientMapper {

    ClientEntity createRequestDtoToEntity(@NotNull ClientDtoCreateRequest dto);

    ClientEntity updateRequestDtoToEntity(@NotNull ClientDtoUpdateRequest dto);

    ClientDtoResponse entityToResponseDto(@NotNull ClientEntity entity);

}
