package com.trackerauth.AuthServer.domains.client.validation.service;

import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;

public interface ClientValidationService {

    void validate(ClientDtoCreateRequest dto);

    void validate(ClientDtoUpdateRequest dto);

}
