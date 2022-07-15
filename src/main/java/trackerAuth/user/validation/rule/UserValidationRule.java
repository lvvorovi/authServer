package trackerAuth.user.validation.rule;

import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoUpdateRequest;

public interface UserValidationRule {

    void validate(UserDtoCreateRequest dto);

    void validate(UserDtoUpdateRequest dto);

}
