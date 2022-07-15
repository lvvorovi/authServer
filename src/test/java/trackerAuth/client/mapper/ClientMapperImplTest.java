package trackerAuth.client.mapper;

import trackerAuth.client.domain.ClientEntity;
import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoResponse;
import trackerAuth.client.dto.ClientDtoUpdateRequest;
import trackerAuth.client.mapper.ClientMapperImpl;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ContextConfiguration(classes = {ClientMapperImpl.class, ModelMapper.class})
class ClientMapperImplTest {

    @Autowired
    ClientMapperImpl victim;

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

    private ClientDtoUpdateRequest newClientDtoUpdateRequest(ClientEntity entity) {
        ClientDtoUpdateRequest updateRequest = new ClientDtoUpdateRequest();
        updateRequest.setId(entity.getId());
        updateRequest.setClientId(entity.getClientId());
        updateRequest.setName(entity.getName());
        updateRequest.setSecret(entity.getSecret());
        updateRequest.setRedirectUri(entity.getRedirectUri());
        updateRequest.setScope(entity.getScope());
        updateRequest.setAuthenticationMethod(entity.getAuthenticationMethod());
        updateRequest.setAuthorizationGrantType(entity.getAuthorizationGrantType());
        return updateRequest;
    }

    private ClientDtoCreateRequest newClientDtoCreateRequest(ClientEntity entity) {
        ClientDtoCreateRequest createRequest = new ClientDtoCreateRequest();
        createRequest.setName(entity.getName());
        createRequest.setSecret(entity.getSecret());
        createRequest.setRedirectUri(entity.getRedirectUri());
        createRequest.setScope(entity.getScope());
        createRequest.setAuthenticationMethod(entity.getAuthenticationMethod());
        createRequest.setAuthorizationGrantType(entity.getAuthorizationGrantType());
        return createRequest;
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
    void createRequestDtoToEntity_maps() {
        ClientEntity entity = newClientEntity();
        ClientDtoCreateRequest createRequest = newClientDtoCreateRequest(entity);

        ClientEntity result = victim.createRequestDtoToEntity(createRequest);

        assertNull(result.getId());
        assertNull(result.getClientId());
        assertEquals(createRequest.getName(), result.getName());
        assertEquals(createRequest.getSecret(), result.getSecret());
        assertEquals(createRequest.getRedirectUri(), result.getRedirectUri());
        assertEquals(createRequest.getScope(), result.getScope());
        assertEquals(createRequest.getAuthenticationMethod(), result.getAuthenticationMethod());
        assertEquals(createRequest.getAuthorizationGrantType(), result.getAuthorizationGrantType());
    }

    @Test
    void updateRequestDtoToEntity_maps() {
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest updateRequest = newClientDtoUpdateRequest(entity);

        ClientEntity result = victim.updateRequestDtoToEntity(updateRequest);

        assertEquals(updateRequest.getId(), result.getId());
        assertEquals(updateRequest.getClientId(), result.getClientId());
        assertEquals(updateRequest.getName(), result.getName());
        assertEquals(updateRequest.getSecret(), result.getSecret());
        assertEquals(updateRequest.getRedirectUri(), result.getRedirectUri());
        assertEquals(updateRequest.getScope(), result.getScope());
        assertEquals(updateRequest.getAuthenticationMethod(), result.getAuthenticationMethod());
        assertEquals(updateRequest.getAuthorizationGrantType(), result.getAuthorizationGrantType());
    }

    @Test
    void entityToResponseDto_maps() {
        ClientEntity entity = newClientEntity();
        ClientDtoResponse response = newClientDtoResponse(entity);

        ClientDtoResponse result = victim.entityToResponseDto(entity);

        assertEquals(response.getId(), result.getId());
        assertEquals(response.getClientId(), result.getClientId());
        assertEquals(response.getName(), result.getName());
        assertEquals(response.getSecret(), result.getSecret());
        assertEquals(response.getRedirectUri(), result.getRedirectUri());
        assertEquals(response.getScope(), result.getScope());
        assertEquals(response.getAuthenticationMethod(), result.getAuthenticationMethod());
        assertEquals(response.getAuthorizationGrantType(), result.getAuthorizationGrantType());
    }
}