package trackerAuth.core.config.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import trackerAuth.core.config.app.AppValuesHolder;
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

//    http://localhost:9090/oauth2/authorize?response_type=code&client_id=90ef5341-7e5a-482a-a6fb-049db7992301&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized&code_challenge=dPz8OFyP8g1yHdxiH6lyoQnALCUbUUclGilMBtf7ksg&code_challenge_method=S256
//    http://localhost:9090/oauth2/token?code=gHAag_sIYPjESQU4A7m0QQwP4FD6T9aRNusgLeUtAn3QolLoiAfl2nyWmdlwEmhR7dMStQXBqjCjxf5ztdBszHSQVcbgu97vRABEJ7jiyUg_ctSW5v8PIRgP7NIukayM&redirect_uri=http://127.0.0.1:3000/authorized&grant_type=authorization_code&code_verifier=2iQIug_f4iSFuun2ktC02Yh4TpMykSEUxdNpYk_er2k

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
