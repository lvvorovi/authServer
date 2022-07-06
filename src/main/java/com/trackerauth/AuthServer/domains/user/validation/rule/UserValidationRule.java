package com.trackerauth.AuthServer.domains.user.validation.rule;

import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;

public interface UserValidationRule {

    void validate(UserDtoCreateRequest dto);

    void validate(UserDtoUpdateRequest dto);

}
