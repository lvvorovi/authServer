package com.trackerauth.AuthServer.config.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.trackerauth.AuthServer.domains.client.service.RegisteredClientRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class AuthorizationConfig {

    private final RegisteredClientRepositoryImpl repository;
    private final RSAKeyUtil rsaKeyUtil;

//    http://127.0.0.1:9090/oauth2/authorize?response_type=code&client_id=clientId&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized&code_challenge=dPz8OFyP8g1yHdxiH6lyoQnALCUbUUclGilMBtf7ksg&code_challenge_method=S256

    @Bean
    public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
       http.formLogin().permitAll()
               .and()
               .cors().disable()
                .csrf().disable();
       return http.build();
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("http://127.0.0.1:9090")
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = rsaKeyUtil.getKey();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
}
