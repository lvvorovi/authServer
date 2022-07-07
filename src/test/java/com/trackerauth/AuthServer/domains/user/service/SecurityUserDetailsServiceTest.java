package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.SecurityUserDetails;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import com.trackerauth.AuthServer.domains.user.validation.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
    private UserResponseDto newUserResponseDto(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        return dto;
    }
    private SecurityUserDetails newSecurityUserDetails(UserResponseDto dto) {
        return new SecurityUserDetails(dto);
    }

    @Test
    void loadUserByUsername_returnsSecurityUserDetails(CapturedOutput output) {
        UserResponseDto responseDto = newUserResponseDto(newUserEntity());
        SecurityUserDetails securityUserDetails = newSecurityUserDetails(responseDto);
        String username = responseDto.getUsername();
        when(userService.findByUserName(username)).thenReturn(responseDto);

        UserDetails result = victim.loadUserByUsername(username);

        assertEquals(securityUserDetails, result);
        verify(userService, times(1)).findByUserName(username);
        assertThat(output.toString()).contains("loadUserByUsername " + username);
        assertThat(output.toString()).contains("loaded SecurityUserDetails " + responseDto);
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
        assertThat(output.toString()).contains("loadUserByUsername " + username);
        assertThat(output.toString()).doesNotContain("loaded SecurityUserDetails ");
    }
}