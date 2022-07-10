package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.config.app.AppValuesHolder;
import com.trackerauth.AuthServer.config.security.SecurityConfig;
import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecurityConfig.class, ClientController.class, JsonUtil.class, AppValuesHolder.class, ClientAuthenticationMethod.class})
class ClientControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    JsonUtil jsonUtil;
    @MockBean
    ClientService service;


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

    @WithMockUser
    @Test
    void findById_whenId_thenReturnsClientDtoResponseAsJsonAnd200() throws Exception {
        ClientDtoResponse response = newClientDtoResponse(newClientEntity());
        when(service.findById(response.getId())).thenReturn(response);

        mvc.perform(get(linkTo(methodOn(ClientController.class).findById(response.getId())).toString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost" +
                                linkTo(methodOn(ClientController.class).findById(response.getId())))));
        verify(service, times(1)).findById(response.getId());
    }

    @Test
    void findById_whenIsNotAuthenticated_then401() throws Exception {
        String id = "id";
        mvc.perform(get(linkTo(methodOn(ClientController.class).findById(id)).toString())
                        .with(csrf()))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @Test
    void save_whenClientDtoRequest_thenDelegatesToService_ReturnsResponseDto_status201() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoCreateRequest request = newClientDtoCreateRequest(entity);
        ClientDtoResponse response = newClientDtoResponse(entity);
        when(service.save(request)).thenReturn(response);

        mvc.perform(post(linkTo(ClientController.class).toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)
                                .replace(stringToReplace(), stringOfReplacement())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost" +
                                linkTo(methodOn(ClientController.class).findById(response.getId())))));

        verify(service, times(1)).save(request);
    }

    @Test
    void save_whenWrongJson_thenReturns400() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoCreateRequest request = newClientDtoCreateRequest(entity);
        request.setName(null);

        mvc.perform(post(linkTo(ClientController.class).toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().is(400));

        verifyNoInteractions(service);
    }

    @Test
    void update_whenIsNotAuthenticated_then401() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest request = newClientDtoUpdateRequest(entity);
        ClientDtoResponse response = newClientDtoResponse(entity);

        mvc.perform(put(linkTo(ClientController.class).toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @WithMockUser
    @Test
    void update_whenClientDtoUpdateRequest_thenDelegatesToService_returnsSavedValue_status204() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest request = newClientDtoUpdateRequest(entity);
        ClientDtoResponse response = newClientDtoResponse(entity);
        when(service.update(request)).thenReturn(response);

        mvc.perform(put(linkTo(ClientController.class).toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)
                                .replace(stringToReplace(), stringOfReplacement())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost" +
                                linkTo(methodOn(ClientController.class).findById(response.getId())))));

        verify(service, times(1)).update(request);
    }

    @WithMockUser
    @Test
    void update_whenClientDtoRequestNameNull_thenReturn400() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest request = newClientDtoUpdateRequest(entity);
        request.setName(null);

        mvc.perform(put(linkTo(ClientController.class).toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().is(400));

        verifyNoInteractions(service);
    }

    @Test
    void deleteById_whenIsNotAuthenticated_then401() throws Exception {
        String id = "id";
        mvc.perform(delete(linkTo(methodOn(ClientController.class).findById(id)).toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @WithMockUser
    @Test
    void deleteById_whenID_thenDelegatesToService_and204() throws Exception {
        String id = "id";
        doNothing().when(service).deleteById(id);

        mvc.perform(delete(linkTo(methodOn(ClientController.class).findById(id)).toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));

        verify(service, times(1)).deleteById(id);
    }


    private String stringToReplace() {
        return "\"authenticationMethod\":{\"value\":\"client_secret_basic\"},\"authorizationGrantType\":{\"value\":\"authorization_code\"}";
    }

    private String stringOfReplacement() {
        return "\"authenticationMethod\":\"client_secret_basic\",\"authorizationGrantType\":\"authorization_code\"";
    }
}