package com.trackerauth.AuthServer.security;

import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.SecurityUserDetails;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import com.trackerauth.AuthServer.domains.user.service.SecurityUserDetailsService;
import com.trackerauth.AuthServer.domains.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SecurityUserDetailsServiceTest {

    @Mock
    UserService userService;
    @Mock
    SecurityUserDetails userDetails;
    @InjectMocks
    SecurityUserDetailsService victim;

    private UserEntity userEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        entity.setUsername("user@user.com");
        return entity;
    }

    private UserResponseDto userResponseDto(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Test
    void loadUserByUsername_returnsUserDetails() {
        UserResponseDto userResponseDto = userResponseDto(userEntity());
        SecurityUserDetails expectedUserDetails = new SecurityUserDetails(userResponseDto);
        when(userService.findByUserName(any())).thenReturn(userResponseDto);
        UserDetails returnedUserDetails = victim.loadUserByUsername(any());
        assertEquals(expectedUserDetails.getUsername(), returnedUserDetails.getUsername());

        assertEquals(expectedUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                returnedUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));

        assertEquals(expectedUserDetails.getPassword(), returnedUserDetails.getPassword());
    }
}