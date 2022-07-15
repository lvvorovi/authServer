package trackerAuth.client.mapper;

import trackerAuth.client.domain.ClientEntity;
import trackerAuth.client.dto.ClientDtoResponse;
import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoUpdateRequest;
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
