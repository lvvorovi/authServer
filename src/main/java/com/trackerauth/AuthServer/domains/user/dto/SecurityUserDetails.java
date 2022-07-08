package com.trackerauth.AuthServer.domains.user.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString
@EqualsAndHashCode
public class SecurityUserDetails implements UserDetails {

    private final UserDtoResponse userDtoResponse;

    public SecurityUserDetails(UserDtoResponse userDtoResponse) {
        this.userDtoResponse = userDtoResponse;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    @Override
    public String getPassword() {
        return userDtoResponse.getPassword();
    }

    @Override
    public String getUsername() {
        return userDtoResponse.getUsername();
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
