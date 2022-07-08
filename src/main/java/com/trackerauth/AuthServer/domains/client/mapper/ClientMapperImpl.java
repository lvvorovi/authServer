package com.trackerauth.AuthServer.domains.client.mapper;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperImpl implements ClientMapper {

    private final ModelMapper mapper;

    public ClientMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ClientEntity createRequestDtoToEntity(ClientDtoCreateRequest dto) {
        return mapper.map(dto, ClientEntity.class);
    }

    @Override
    public ClientEntity updateRequestDtoToEntity(ClientDtoUpdateRequest dto) {
        return mapper.map(dto, ClientEntity.class);
    }

    @Override
    public ClientDtoResponse entityToResponseDto(ClientEntity entity) {
        return mapper.map(entity, ClientDtoResponse.class);
    }
}
