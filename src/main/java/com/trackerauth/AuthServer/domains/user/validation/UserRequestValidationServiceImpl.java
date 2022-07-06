package com.trackerauth.AuthServer.domains.user.validation;

import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.validation.rule.UserValidationRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRequestValidationServiceImpl implements UserRequestValidationService {

    private final List<UserValidationRule> validationRuleList;

    public UserRequestValidationServiceImpl(List<UserValidationRule> validationRuleList) {
        this.validationRuleList = validationRuleList;
    }

    public void validate(UserDtoCreateRequest dto) {
        validationRuleList.forEach(rule -> rule.validate(dto));
    }

    public void validate(UserDtoUpdateRequest dto) {
        validationRuleList.forEach(rule -> rule.validate(dto));
    }
}
