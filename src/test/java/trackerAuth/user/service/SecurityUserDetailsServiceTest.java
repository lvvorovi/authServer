package trackerAuth.user.service;

import trackerAuth.user.domain.UserEntity;
import trackerAuth.user.dto.SecurityUserDetails;
import trackerAuth.user.dto.UserDtoResponse;
import trackerAuth.user.scope.UserScope;
import trackerAuth.user.validation.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
@ActiveProfiles("logging-test")
class SecurityUserDetailsServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    SecurityUserDetailsService victim;

    private UserEntity newUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId("123456789");
        entity.setUsername("john@email.com");
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        return entity;
    }

    private UserDtoResponse newUserResponseDto(UserEntity entity) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        return dto;
    }

    private SecurityUserDetails newSecurityUserDetails(UserDtoResponse dto) {
        return new SecurityUserDetails(dto);
    }

    @Test
    void loadUserByUsername_returnsSecurityUserDetails(CapturedOutput output) {
        UserDtoResponse responseDto = newUserResponseDto(newUserEntity());
        SecurityUserDetails expected = newSecurityUserDetails(responseDto);
        String username = responseDto.getUsername();
        when(userService.findByUserName(username)).thenReturn(responseDto);

        UserDetails result = victim.loadUserByUsername(username);

        assertEquals(expected, result);
        verify(userService, times(1)).findByUserName(username);
        assertThat(output.toString()).contains("Requested to loadUserByUsername() with id = " + username);
        assertThat(output.toString()).contains("Loaded UserDtoResponse " + responseDto);
        assertThatNoException().isThrownBy(() -> victim.loadUserByUsername(username));
    }

    @Test
    void loadUserByUsername_throwsUsernameNotFoundException(CapturedOutput output) {
        String username = "username";
        when(userService.findByUserName(username)).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> victim.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("user with username " + username + " was not found");

        verify(userService, times(1)).findByUserName(username);
        assertThat(output.toString()).contains("Requested to loadUserByUsername() with id = " + username);
        assertThat(output.toString()).doesNotContain("Loaded UserDtoResponse ");
    }
}