package com.trackerauth.AuthServer.security;

import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserResponseDto userResponseDto;

    public CustomUserDetails(UserResponseDto userResponseDto) {
        this.userResponseDto = userResponseDto;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    @Override
    public String getPassword() {
        return userResponseDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userResponseDto.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
