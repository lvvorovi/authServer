package com.trackerauth.AuthServer.domains.user.service;

import com.trackerauth.AuthServer.domains.user.dto.SecurityUserDetails;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.validation.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public SecurityUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername {}", username);
        UserResponseDto userResponseDto;
        try {
            userResponseDto = userService.findByUserName(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("user with username " + username + " was not found");
        }
        log.debug("loaded SecurityUserDetails {}", userResponseDto);
        return new SecurityUserDetails(userResponseDto);
    }
}
