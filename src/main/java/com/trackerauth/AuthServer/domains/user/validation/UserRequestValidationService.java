package com.trackerauth.AuthServer.domains.user.validation;

import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;

public interface UserRequestValidationService {

    void validate(UserDtoCreateRequest dto);

    void validate(UserDtoUpdateRequest dto);

}
