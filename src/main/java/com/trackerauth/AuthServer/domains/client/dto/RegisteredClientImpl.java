package com.trackerauth.AuthServer.domains.client.dto;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.util.Set;

@AllArgsConstructor
public class RegisteredClientImpl extends RegisteredClient {

    private final ClientDtoResponse response;

    @Override
    public String getId() {
        return response.getId();
    }

    @Override
    public String getClientId() {
        return response.getClientId();
    }

    @Override
    public String getClientSecret() {
        return response.getSecret();
    }

    @Override
    public String getClientName() {
        return response.getName();
    }

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethods() {
        return Set.of(response.getAuthenticationMethod());
    }

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypes() {
        return Set.of(response.getAuthorizationGrantType());
    }

    @Override
    public Set<String> getRedirectUris() {
        return Set.of(response.getRedirectUri());
    }

    @Override
    public Set<String> getScopes() {
        return Set.of(response.getScope());
    }

    @Override
    public ClientSettings getClientSettings() {
        return ClientSettings.builder()
                .requireAuthorizationConsent(true)
                .build();
    }

    @Override
    public TokenSettings getTokenSettings() {
        return TokenSettings.builder()
                .reuseRefreshTokens(true)
                .refreshTokenTimeToLive(Duration.ofDays(30))
                .accessTokenTimeToLive(Duration.ofDays(10))
                .build();
    }
}
