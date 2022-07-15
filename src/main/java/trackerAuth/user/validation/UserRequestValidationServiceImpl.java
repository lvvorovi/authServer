package trackerAuth.user.validation;

import trackerAuth.user.validation.rule.UserValidationRule;
import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoUpdateRequest;
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
