package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.config.app.AppValuesHolder;
import com.trackerauth.AuthServer.domains.client.ClientEntity;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoCreateRequest;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoResponse;
import com.trackerauth.AuthServer.domains.client.dto.ClientDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.client.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({ClientController.class, JsonUtil.class, AppValuesHolder.class})
class ClientControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    JsonUtil jsonUtil;

    @MockBean
    ClientService service;


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

    @WithMockUser
    @Test
    void findById_whenId_returnsClientDtoResponseAsJsonAnd200() throws Exception {
        ClientDtoResponse response = newClientDtoResponse(newClientEntity());
        when(service.findById(response.getId())).thenReturn(response);

        mvc.perform(get(linkTo(methodOn(ClientController.class).findById(response.getId())).toString()))
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
        mvc.perform(get(linkTo(methodOn(ClientController.class).findById(id)).toString()))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @Test
    void save_whenClientDtoRequest_thenDelegatesToService_and_returnsResponseDto_and201() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoCreateRequest request = newClientDtoCreateRequest(entity);
        ClientDtoResponse response = newClientDtoResponse(entity);
        when(service.save(request)).thenReturn(response);

        mvc.perform(post(linkTo(ClientController.class).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost" +
                                linkTo(methodOn(ClientController.class).findById(response.getId())))));

        verify(service, times(1)).save(request);
    }

    @Test
    void save_whenClientDtoRequestNameNull_thenReturns400() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoCreateRequest request = newClientDtoCreateRequest(entity);
        request.setName(null);

        mvc.perform(post(linkTo(ClientController.class).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().is(400));

        verifyNoInteractions(service);
    }

    @WithMockUser
    @Test
    void update_whenClientDtoRequestNameNull_thenReturn400() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest request = newClientDtoUpdateRequest(entity);
        request.setName(null);

        mvc.perform(put(linkTo(ClientController.class).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().is(400));

        verifyNoInteractions(service);
    }

    @WithMockUser
    @Test
    void update_whenClientDtoRequest_thenDelegatesToService_and_returnsSavedValue_status204() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest request = newClientDtoUpdateRequest(entity);
        ClientDtoResponse response = newClientDtoResponse(entity);
        when(service.update(request)).thenReturn(response);

        mvc.perform(put(linkTo(ClientController.class).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost" +
                                linkTo(methodOn(ClientController.class).findById(response.getId())))));

        verify(service, times(1)).update(request);
    }

    @Test
    void update_whenIsNotAuthenticated_then401() throws Exception {
        ClientEntity entity = newClientEntity();
        ClientDtoUpdateRequest request = newClientDtoUpdateRequest(entity);
        ClientDtoResponse response = newClientDtoResponse(entity);

        mvc.perform(put(linkTo(ClientController.class).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @Test
    void deleteById_whenIsNotAuthenticated_then401() throws Exception {
        String id = "id";
        mvc.perform(delete(linkTo(methodOn(ClientController.class).findById(id)).toString())
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
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));

        verify(service, times(1)).deleteById(id);
    }


}