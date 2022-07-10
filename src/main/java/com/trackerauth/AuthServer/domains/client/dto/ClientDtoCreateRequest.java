package com.trackerauth.AuthServer.domains.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
@Data
public class ClientDtoCreateRequest {

    @NotBlank
    @Size(min = 3, max = 30, message = "size from 3 to 30")
    private String name;
    @NotBlank
    @Size(min = 3, max = 30, message = "size from 3 to 30")
    private String secret;
    @NotBlank
    private String redirectUri;
    @NotNull
    private String scope;
    @NotNull
    private ClientAuthenticationMethod authenticationMethod;
    @NotNull
    private AuthorizationGrantType authorizationGrantType;

}
