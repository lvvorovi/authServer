package trackerAuth.client.service;

import trackerAuth.client.domain.ClientEntity;
import trackerAuth.client.dto.ClientDtoResponse;
import trackerAuth.client.service.ClientService;
import trackerAuth.client.service.RegisteredClientRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RegisteredClientRepositoryImplTest {

    @Mock
    ClientService service;
    @InjectMocks
    RegisteredClientRepositoryImpl victim;

    RegisteredClient newRegisteredClient(ClientDtoResponse response) {
        return RegisteredClient
                .withId(response.getId())
                .clientId(response.getClientId())
                .clientName(response.getName())
                .clientSecret(response.getSecret())
                .scope(response.getScope())
                .clientAuthenticationMethod(response.getAuthenticationMethod())
                .authorizationGrantType(response.getAuthorizationGrantType())
                .redirectUri(response.getRedirectUri())
                .tokenSettings(TokenSettings.builder()
                        .reuseRefreshTokens(true)
                        .refreshTokenTimeToLive(Duration.ofDays(30))
                        .accessTokenTimeToLive(Duration.ofDays(10))
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build())
                .build();
    }
    private ClientEntity newClientEntity() {
        ClientEntity entity = new ClientEntity();
        entity.setId("id");
        entity.setClientId("clientId");
        entity.setName("clientName");
        entity.setSecret("clientSecret");
        entity.setRedirectUri("redirectUri");
        entity.setScope(OidcScopes.OPENID);
        entity.setAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        entity.setAuthorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        return entity;
    }
    private ClientDtoResponse newClientDtoResponse(ClientEntity entity) {
        ClientDtoResponse responseDto = new ClientDtoResponse();
        responseDto.setId(entity.getId());
        responseDto.setClientId(entity.getClientId());
        responseDto.setName(entity.getName());
        responseDto.setSecret(entity.getSecret());
        responseDto.setRedirectUri(entity.getRedirectUri());
        responseDto.setScope(entity.getScope());
        responseDto.setAuthenticationMethod(entity.getAuthenticationMethod());
        responseDto.setAuthorizationGrantType(entity.getAuthorizationGrantType());
        return responseDto;
    }

    @Test
    void save() {
        RegisteredClient registeredClientMock = mock(RegisteredClient.class);
        assertThatNoException().isThrownBy(() ->
                victim.save(registeredClientMock));
        verifyNoInteractions(service, registeredClientMock);
    }

    @Test
    void findById_whenServiceReturnsNull_thenReturnsNull() {
        String id = "id";
        when(service.findById(id)).thenReturn(null);

        RegisteredClient result = victim.findById(id);

        assertNull(result);
        verify(service, times(1)).findById(id);
    }

    @Test
    void findById_whenServiceReturnsClient_thenReturnsRegisteredClient() {
        ClientDtoResponse response = newClientDtoResponse(newClientEntity());
        RegisteredClient expected = newRegisteredClient(response);
        when(service.findById(response.getId())).thenReturn(response);

        RegisteredClient result = victim.findById(response.getId());

        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getClientId(), result.getClientId());
        assertEquals(expected.getClientName(), result.getClientName());
        assertEquals(expected.getClientSecret(), result.getClientSecret());
        assertEquals(expected.getScopes(), result.getScopes());
        assertEquals(expected.getClientAuthenticationMethods(), result.getClientAuthenticationMethods());
        assertEquals(expected.getAuthorizationGrantTypes(), result.getAuthorizationGrantTypes());
        assertEquals(expected.getRedirectUris(), result.getRedirectUris());
        assertEquals(expected.getTokenSettings(), result.getTokenSettings());
        assertEquals(expected.getClientSettings(), result.getClientSettings());
        verify(service, times(1)).findById(response.getId());
    }

    @Test
    void findByClientId_whenServiceReturnsNull_thenReturnsNull() {
        String clientId = "clientId";
        when(service.findByClientId(clientId)).thenReturn(null);

        RegisteredClient result = victim.findByClientId(clientId);

        assertNull(result);
        verify(service, times(1)).findByClientId(clientId);
    }

    @Test
    void findByClientId_whenServiceReturnsClient_thenReturnsRegisteredClient() {
        ClientDtoResponse response = newClientDtoResponse(newClientEntity());
        RegisteredClient expected = newRegisteredClient(response);
        when(service.findByClientId(response.getClientId())).thenReturn(response);

        RegisteredClient result = victim.findByClientId(response.getClientId());

        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getClientId(), result.getClientId());
        assertEquals(expected.getClientName(), result.getClientName());
        assertEquals(expected.getClientSecret(), result.getClientSecret());
        assertEquals(expected.getScopes(), result.getScopes());
        assertEquals(expected.getClientAuthenticationMethods(), result.getClientAuthenticationMethods());
        assertEquals(expected.getAuthorizationGrantTypes(), result.getAuthorizationGrantTypes());
        assertEquals(expected.getRedirectUris(), result.getRedirectUris());
        assertEquals(expected.getTokenSettings(), result.getTokenSettings());
        assertEquals(expected.getClientSettings(), result.getClientSettings());
        verify(service, times(1)).findByClientId(response.getClientId());
    }

    @Test
    void findByClientId() {
    }
}