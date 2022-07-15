package trackerAuth.client.validation.service;

import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoUpdateRequest;

public interface ClientValidationService {

    void validate(ClientDtoCreateRequest dto);

    void validate(ClientDtoUpdateRequest dto);

}
