package com.trackerauth.AuthServer.config.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.trackerauth.AuthServer.config.app.AppValuesHolder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class AuthorizationConfig {

    private final RSAKeyUtil rsaKeyUtil;
    private final AppValuesHolder valuesHolder;

//    http://127.0.0.1:9090/oauth2/authorize?response_type=code&client_id=clientId&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized&code_challenge=dPz8OFyP8g1yHdxiH6lyoQnALCUbUUclGilMBtf7ksg&code_challenge_method=S256

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        if (valuesHolder.getIsDevelopmentMode()) {
            http
                    .cors().disable()
                    .csrf().disable();
        }

        return http.formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("http://localhost:9090")
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = rsaKeyUtil.getKey();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
}
