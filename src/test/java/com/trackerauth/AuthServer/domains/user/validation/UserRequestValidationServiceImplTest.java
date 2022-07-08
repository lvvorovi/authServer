package com.trackerauth.AuthServer.domains.user.validation;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import com.trackerauth.AuthServer.domains.user.validation.rule.UserRequestNameValidationRule;
import com.trackerauth.AuthServer.domains.user.validation.rule.UserValidationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserRequestValidationServiceImplTest {

    @Mock
    UserRequestNameValidationRule nameValidationRule;

    UserRequestValidationServiceImpl victim;
    List<UserValidationRule> validationRuleList;

    @BeforeEach
    public void setUp() {
        validationRuleList = new LinkedList<>();
        validationRuleList.add(nameValidationRule);
        victim = new UserRequestValidationServiceImpl(validationRuleList);
    }

    private UserEntity newUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId("123456789");
        entity.setUsername("john@email.com");
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        return entity;
    }

    private UserDtoCreateRequest newUserCreateRequestDto(UserEntity entity) {
        UserDtoCreateRequest createRequestDto = new UserDtoCreateRequest();
        createRequestDto.setPassword(entity.getPassword());
        createRequestDto.setUsername(entity.getUsername());
        return createRequestDto;
    }

    private UserDtoUpdateRequest newUserUpdateRequestDto(UserEntity entity) {
        UserDtoUpdateRequest updateRequestDto = new UserDtoUpdateRequest();
        updateRequestDto.setPassword(entity.getPassword());
        updateRequestDto.setUsername(entity.getUsername());
        updateRequestDto.setId(entity.getId());
        return updateRequestDto;
    }

    @Test
    void validate_createRequest_delegatesToAllRules() {
        UserDtoCreateRequest createRequest = newUserCreateRequestDto(newUserEntity());

        victim.validate(createRequest);

        validationRuleList.forEach(rule ->
                verify(rule, times(1)).validate(createRequest));

    }

    @Test
    void validate_updateRequest_delegatesToAllRules() {
        UserDtoUpdateRequest updateRequest = newUserUpdateRequestDto(newUserEntity());

        victim.validate(updateRequest);

        validationRuleList.forEach(rule ->
                verify(rule, times(1)).validate(updateRequest));
    }


}