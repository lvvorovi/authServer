package com.trackerauth.AuthServer.domains.client.mapper;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateRequestClientDto;
import com.trackerauth.AuthServer.domains.client.dto.UpdateRequestClientDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ClientMapper {

    ClientEntity createRequestDtoToEntity(@NotNull CreateRequestClientDto dto);

    ClientEntity updateRequestDtoToEntity(@NotNull UpdateRequestClientDto dto);

    ClientResponseDto entityToResponseDto(@NotNull ClientEntity entity);

}
