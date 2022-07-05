package com.trackerauth.AuthServer.domains.client.mapper;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateRequestClientDto;
import com.trackerauth.AuthServer.domains.client.dto.UpdateRequestClientDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperImpl implements ClientMapper {

    private final ModelMapper mapper;

    public ClientMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ClientEntity createRequestDtoToEntity(CreateRequestClientDto dto) {
        return mapper.map(dto, ClientEntity.class);
    }

    @Override
    public ClientEntity updateRequestDtoToEntity(UpdateRequestClientDto dto) {
        return mapper.map(dto, ClientEntity.class);
    }

    @Override
    public ClientResponseDto entityToResponseDto(ClientEntity entity) {
        return mapper.map(entity, ClientResponseDto.class);
    }
}
