package com.trackerauth.AuthServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppValuesHolder appValuesHolder;

    public SecurityConfig(AppValuesHolder appValuesHolder) {
        this.appValuesHolder = appValuesHolder;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("ssia.jks"), "ssia123".toCharArray()
        );
        converter.setKeyPair(keyFactory.getKeyPair("ssia"));
        return converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (appValuesHolder.getIsDevelopmentMode()) {
            http.csrf().disable();
            http.formLogin().permitAll();
            http.cors().disable();
        }
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/v1/clients").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .anyRequest().authenticated();
    }
}
