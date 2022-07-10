package com.trackerauth.AuthServer.domains.client;

import lombok.Data;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "clients")
public class ClientEntity {

    @Id
    private String id;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_name")
    private String name;
    @Column(name = "client_secret")
    private String secret;
    @Column(name = "redirect_uri")
    private String redirectUri;
    @Column(name = "client_scope")
    private String scope;
    @Column(name = "authentication_method")
    private ClientAuthenticationMethod authenticationMethod;
    @Column(name = "authorization_grant_type")
    private AuthorizationGrantType authorizationGrantType;

}
