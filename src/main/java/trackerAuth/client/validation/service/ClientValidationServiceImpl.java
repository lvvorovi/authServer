package trackerAuth.client.validation.service;

import trackerAuth.client.validation.rule.ClientValidationRule;
import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoUpdateRequest;
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
