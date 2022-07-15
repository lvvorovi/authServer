package trackerAuth.user.service;

import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoResponse;
import trackerAuth.user.dto.UserDtoUpdateRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
public interface UserService {

    UserDtoResponse findByUserName(@NotBlank String username);

    UserDtoResponse save(@Valid UserDtoCreateRequest dto);

    UserDtoResponse findById(@NotBlank String id);

    UserDtoResponse update(@Valid UserDtoUpdateRequest dto);

    void deleteById(@NotBlank String id);

}
