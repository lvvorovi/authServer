package com.trackerauth.AuthServer.domains.client.validation.rule;

import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;

public interface ClientValidationRule {

    void validate(ClientDtoCreateRequest dto);

    void validate(ClientDtoUpdateRequest dto);

}
