package trackerAuth.client.validation.rule;

import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoUpdateRequest;

public interface ClientValidationRule {

    void validate(ClientDtoCreateRequest dto);

    void validate(ClientDtoUpdateRequest dto);

}
