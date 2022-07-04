package com.trackerauth.AuthServer.domains.client.mapper;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateClientDto;
import com.trackerauth.AuthServer.domains.client.dto.UpdateClientDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperImpl implements ClientMapper {

    private final ModelMapper mapper;

    public ClientMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ClientEntity createDtoToEntity(CreateClientDto dto) {
        return mapper.map(dto, ClientEntity.class);
    }

    @Override
    public ClientEntity updateDtoToEntity(UpdateClientDto dto) {
        return mapper.map(dto, ClientEntity.class);
    }

    @Override
    public ClientResponseDto entityToDto(ClientEntity entity) {
        return mapper.map(entity, ClientResponseDto.class);
    }
}
