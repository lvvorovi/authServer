package com.trackerauth.AuthServer.domains.client.validation.service;

import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.validation.rule.ClientValidationRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientValidationServiceImpl implements ClientValidationService {

    private final List<ClientValidationRule> validationRuleList;

    public ClientValidationServiceImpl(List<ClientValidationRule> validationRuleList) {
        this.validationRuleList = validationRuleList;
    }

    @Override
    public void validate(ClientDtoCreateRequest dto) {
        validationRuleList.forEach(rule -> rule.validate(dto));
    }

    @Override
    public void validate(ClientDtoUpdateRequest dto) {
        validationRuleList.forEach(rule -> rule.validate(dto));
    }
}
