package com.trackerauth.AuthServer.config;

import com.trackerauth.AuthServer.security.CustomClientDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final CustomClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter converter;

    public AuthorizationServerConfig(CustomClientDetailsService clientDetailsService, AuthenticationManager authenticationManager, TokenStore tokenStore, JwtAccessTokenConverter converter) {
        this.clientDetailsService = clientDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
        this.converter = converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(converter);
    }
}
