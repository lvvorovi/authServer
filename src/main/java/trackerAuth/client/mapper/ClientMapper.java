package trackerAuth.client.mapper;

import trackerAuth.client.domain.ClientEntity;
import trackerAuth.client.dto.ClientDtoResponse;
import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoUpdateRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ClientMapper {

    ClientEntity createRequestDtoToEntity(@NotNull ClientDtoCreateRequest dto);

    ClientEntity updateRequestDtoToEntity(@NotNull ClientDtoUpdateRequest dto);

    ClientDtoResponse entityToResponseDto(@NotNull ClientEntity entity);

}
