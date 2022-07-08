package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.config.app.AppValuesHolder;
import com.trackerauth.AuthServer.domains.user.UserEntity;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoResponse;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import com.trackerauth.AuthServer.domains.user.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UserController.class, JsonUtil.class, AppValuesHolder.class})
class UserControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    JsonUtil jsonUtil;

    @MockBean
    UserService service;

    private UserEntity newUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId("123456789");
        entity.setUsername("john@email.com");
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        return entity;
    }

    private UserDtoResponse newUserDtoResponse(UserEntity entity) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        return dto;
    }

    private UserDtoCreateRequest newUserDtoCreateResponse(UserEntity entity) {
        UserDtoCreateRequest createRequestDto = new UserDtoCreateRequest();
        createRequestDto.setPassword(entity.getPassword());
        createRequestDto.setUsername(entity.getUsername());
        return createRequestDto;
    }

    private UserDtoUpdateRequest newUserDtoUpdateRequest(UserEntity entity) {
        UserDtoUpdateRequest updateRequestDto = new UserDtoUpdateRequest();
        updateRequestDto.setPassword(entity.getPassword());
        updateRequestDto.setUsername(entity.getUsername());
        updateRequestDto.setId(entity.getId());
        return updateRequestDto;
    }

    @WithMockUser
    @Test
    void findById_returnsUserDtoResponseAsJson() throws Exception {
        UserDtoResponse response = newUserDtoResponse(newUserEntity());
        String id = "id";
        response.setId(id);
        when(service.findById(id)).thenReturn(response);

        mvc.perform(get("/api/v1/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.username", is(response.getUsername())))
                .andExpect(jsonPath("$.scope", is(response.getScope().getCode())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/api/v1/users/" + id)));

        verify(service, times(1)).findById(id);
    }

    @Test
    void findById_whenIsNotAuthenticated_thenReturn401() throws Exception {
        mvc.perform(get("/api/v1/users/any")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @Test
    void save_whenUserDtoCreateRequest_thenDelegatesToRepo_andReturnsSavedValue() throws Exception {
        UserEntity entity = newUserEntity();
        UserDtoCreateRequest createRequest = newUserDtoCreateResponse(entity);
        UserDtoResponse response = newUserDtoResponse(entity);
        when(service.save(createRequest)).thenReturn(response);

        mvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.username", is(response.getUsername())))
                .andExpect(jsonPath("$.scope", is(response.getScope().getCode())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/api/v1/users/" + response.getId())));

        verify(service, times(1)).save(createRequest);
    }

    @Test
    void save_whenWrongJson_thenReturn400() throws Exception {
        UserEntity entity = newUserEntity();
        UserDtoCreateRequest createRequest = newUserDtoCreateResponse(entity);
        createRequest.setUsername(null);

        mvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(createRequest)))
                .andExpect(status().is(400));

        verifyNoInteractions(service);
    }

    @Test
    void update_whenIsNotAuthenticated_thenReturn401() throws Exception {
        mvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @WithMockUser
    @Test
    void update_whenUserDtoUpdateRequest_thenReturnUpdatedValue() throws Exception {
        UserEntity entity = newUserEntity();
        UserDtoUpdateRequest updateRequest = newUserDtoUpdateRequest(entity);
        UserDtoResponse response = newUserDtoResponse(entity);
        when(service.update(updateRequest)).thenReturn(response);


        mvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.scope", is(response.getScope().getCode())))
                .andExpect(jsonPath("$.username", is(response.getUsername())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/api/v1/users/" + response.getId())));

        verify(service, times(1)).update(updateRequest);
    }

    @Test
    void deleteById_whenIsNotAuthenticated_thenReturn401() throws Exception {
        mvc.perform(delete("/api/v1/users/any"))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @WithMockUser
    @Test
    void deleteById_requestedId_thenDelegateToServiceAndReturn204() throws Exception {
        String id = "id";
        doNothing().when(service).deleteById(id);

        mvc.perform(delete("/api/v1/users/" + id))
                .andExpect(status().is(204));

        verify(service, times(1)).deleteById(id);
    }


}