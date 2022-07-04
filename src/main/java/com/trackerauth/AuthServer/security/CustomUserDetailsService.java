package com.trackerauth.AuthServer.security;

import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername {}", username);
        UserResponseDto userResponseDto;
        try {
            userResponseDto = userService.findByUserName(username);
        } catch (RuntimeException e) {
            throw new UsernameNotFoundException("user not found");
        }
        log.info("loaded CustomUserDetails {}", userResponseDto);
        return new CustomUserDetails(userResponseDto);
    }
}
