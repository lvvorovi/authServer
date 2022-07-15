package trackerAuth.user.validation;

import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoUpdateRequest;

public interface UserRequestValidationService {

    void validate(UserDtoCreateRequest dto);

    void validate(UserDtoUpdateRequest dto);

}
