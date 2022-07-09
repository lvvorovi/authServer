package com.trackerauth.AuthServer.config.security;

import com.trackerauth.AuthServer.config.app.AppValuesHolder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AppValuesHolder valuesHolder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (valuesHolder.getIsDevelopmentMode()) {
            http
                    .cors().disable()
                    .csrf().disable()
                    .formLogin();
        }

        http.oauth2ResourceServer(
            oauth2ResourceServerCustomizer -> oauth2ResourceServerCustomizer.jwt().jwkSetUri(
                    "http://localhost:9090/oauth2/jwks"
            )
        ).authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/v1/clients").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

}
