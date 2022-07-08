package com.trackerauth.AuthServer.domains.client.service;

import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.dto.SecurityClientDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
@ActiveProfiles("logging-test")
class SecurityClientDetailsServiceTest {

    @Mock
    ClientService service;

    @InjectMocks
    SecurityClientDetailsService victim;

    private ClientEntity newClientEntity() {
        ClientEntity entity = new ClientEntity();
        entity.setId("clientId");
        entity.setSecret("clientSecret");
        entity.setName("clientName");
        return entity;
    }

    private ClientDtoUpdateRequest newClientDtoUpdateRequest(ClientEntity entity) {
        ClientDtoUpdateRequest updateRequest = new ClientDtoUpdateRequest();
        updateRequest.setId(entity.getId());
        updateRequest.setName(entity.getName());
        updateRequest.setSecret(entity.getSecret());
        return updateRequest;
    }

    private ClientDtoCreateRequest newClientDtoCreateRequest(ClientEntity entity) {
        ClientDtoCreateRequest createRequest = new ClientDtoCreateRequest();
        createRequest.setName(entity.getName());
        createRequest.setSecret(entity.getSecret());
        return createRequest;
    }

    private ClientDtoResponse newClientDtoResponse(ClientEntity entity) {
        ClientDtoResponse responseDto = new ClientDtoResponse();
        responseDto.setId(entity.getId());
        responseDto.setName(entity.getName());
        responseDto.setSecret(entity.getSecret());
        return responseDto;
    }

    @Test
    void loadClientByClientId_throwsClientRegistrationException(CapturedOutput output) {
        String id = "id";
        when(service.findById(id)).thenThrow(NoSuchClientException.class);

        assertThatThrownBy(() -> victim.loadClientByClientId(id))
                .isInstanceOf(ClientRegistrationException.class)
                .hasMessage("Client with id '" + id + "' was not found");

        verify(service, times(1)).findById(id);
        assertThat(output.toString()).contains("Requested to loadClientByClientId() with id = " + id);
        assertThat(output.toString()).doesNotContain("Loaded ClientDtoResponse ");
    }

    @Test
    void loadClientByClientId_loadsClientDetails_andLogs(CapturedOutput output) {
        ClientDtoResponse response = newClientDtoResponse(newClientEntity());
        SecurityClientDetails expected = new SecurityClientDetails(response);
        when(service.findById(response.getId())).thenReturn(response);

        ClientDetails result = victim.loadClientByClientId(response.getId());

        assertEquals(expected.getClientId(), result.getClientId());
        assertEquals(expected.getClientSecret(), result.getClientSecret());
        assertEquals(expected.getScope(), result.getScope());
        assertEquals(expected.getAuthorities(), result.getAuthorities());
        assertEquals(expected.getAuthorizedGrantTypes(), result.getAuthorizedGrantTypes());
        assertEquals(expected.getAccessTokenValiditySeconds(), result.getAccessTokenValiditySeconds());
        assertEquals(expected.getAdditionalInformation(), result.getAdditionalInformation());
        assertEquals(expected.getRefreshTokenValiditySeconds(), result.getRefreshTokenValiditySeconds());
        assertEquals(expected.getRegisteredRedirectUri(), result.getRegisteredRedirectUri());
        assertEquals(expected.getResourceIds(), result.getResourceIds());
        verify(service, times(1)).findById(response.getId());
        assertThat(output.toString())
                .contains("Requested to loadClientByClientId() with id = " + response.getId());
        assertThat(output.toString()).contains("Loaded ClientDtoResponse " + response);
    }
}