package com.trackerauth.AuthServer.domains.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;

import javax.persistence.Column;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientDtoResponse extends RepresentationModel<ClientDtoResponse> {

    private String id;
    private String clientId;
    private String name;
    @JsonIgnore
    private String secret;
    private String redirectUri;
    private String scope;
    private ClientAuthenticationMethod authenticationMethod;
    private AuthorizationGrantType authorizationGrantType;

}
