/*
package com.trackerauth.AuthServer.domains.client.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class SecurityClientDetails implements ClientDetails {

    private final ClientDtoResponse clientDtoResponse;

    public SecurityClientDetails(ClientDtoResponse clientDtoResponse) {
        this.clientDtoResponse = clientDtoResponse;
    }

    @Override
    public String getClientId() {
        return clientDtoResponse.getId();
    }

    @Override
    public Set<String> getResourceIds() {
        return new HashSet<>();
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientDtoResponse.getSecret();
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return Set.of("read");
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return Set.of("authorization_code");
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Set.of("http://localhost:7070/code");
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 999999999;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 999999999;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }
}
*/
