package trackerAuth.user;

import trackerAuth.JsonUtil;
import trackerAuth.core.config.app.AppValuesHolder;
import trackerAuth.core.config.security.SecurityConfig;
import trackerAuth.user.domain.UserEntity;
import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoResponse;
import trackerAuth.user.dto.UserDtoUpdateRequest;
import trackerAuth.user.scope.UserScope;
import trackerAuth.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

@WebMvcTest({SecurityConfig.class, UserController.class, JsonUtil.class, AppValuesHolder.class})
class UserControllerTest {

    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService service;

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
    void findById_whenId_thenReturnsUserDtoResponseAsJsonAnd200() throws Exception {
        UserDtoResponse response = newUserDtoResponse(newUserEntity());
        String id = "id";
        response.setId(id);
        when(service.findById(id)).thenReturn(response);

        mvc.perform(get(linkTo(methodOn(UserController.class).findById(id)).toUri())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.username", is(response.getUsername())))
                .andExpect(jsonPath("$.scope", is(response.getScope().getCode())))
                .andExpect(jsonPath("$._links.self.href",
                                is("http://localhost" +
                                        linkTo(methodOn(UserController.class).findById(id)))));

        verify(service, times(1)).findById(id);
    }

    @Test
    void findById_whenIsNotAuthenticated_thenReturn401() throws Exception {
        mvc.perform(get(linkTo(UserController.class) + "any")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @Test
    void save_whenUserDtoCreateRequest_thenDelegatesToService_ReturnsSavedValue() throws Exception {
        UserEntity entity = newUserEntity();
        UserDtoCreateRequest createRequest = newUserDtoCreateResponse(entity);
        UserDtoResponse response = newUserDtoResponse(entity);
        when(service.save(createRequest)).thenReturn(response);

        mvc.perform(post(linkTo(UserController.class).toUri())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.username", is(response.getUsername())))
                .andExpect(jsonPath("$.scope", is(response.getScope().getCode())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost" +
                                linkTo(methodOn(UserController.class).findById(response.getId())))));

        verify(service, times(1)).save(createRequest);
    }

    @Test
    void save_whenWrongJson_thenReturn400() throws Exception {
        UserEntity entity = newUserEntity();
        UserDtoCreateRequest createRequest = newUserDtoCreateResponse(entity);
        createRequest.setUsername(null);

        mvc.perform(post(linkTo(UserController.class).toUri())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(createRequest)))
                .andExpect(status().is(400));

        verifyNoInteractions(service);
    }

    @Test
    void update_whenIsNotAuthenticated_thenReturn401() throws Exception {
        mvc.perform(put(linkTo(UserController.class).toUri())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @WithMockUser
    @Test
    void update_whenUserDtoUpdateRequest_thenDelegatesToService_returnsSavedValue_status204() throws Exception {
        UserEntity entity = newUserEntity();
        UserDtoUpdateRequest updateRequest = newUserDtoUpdateRequest(entity);
        UserDtoResponse response = newUserDtoResponse(entity);
        when(service.update(updateRequest)).thenReturn(response);

        mvc.perform(put(linkTo(UserController.class).toUri())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.scope", is(response.getScope().getCode())))
                .andExpect(jsonPath("$.username", is(response.getUsername())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost" +
                                        linkTo(methodOn(UserController.class).findById(response.getId())))));

        verify(service, times(1)).update(updateRequest);
    }

    @Test
    @WithMockUser
    void update_whenUserDtoUpdateRequestNameNull_thenReturn400() throws Exception {
        UserDtoUpdateRequest request = newUserDtoUpdateRequest(newUserEntity());
        request.setUsername(null);

        mvc.perform(put(linkTo(UserController.class).toUri())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtil.objectToJson(request)))
                .andExpect(status().is(400));

        verifyNoInteractions(service);
    }

    @Test
    void deleteById_whenIsNotAuthenticated_thenReturn401() throws Exception {
        mvc.perform(delete(linkTo(methodOn(UserController.class).deleteById("any")).toUri())
                        .with(csrf()))
                .andExpect(status().is(401));

        verifyNoInteractions(service);
    }

    @WithMockUser
    @Test
    void deleteById_requestedId_thenDelegateToServiceAndReturn204() throws Exception {
        String id = "id";
        doNothing().when(service).deleteById(id);

        mvc.perform(delete(linkTo(methodOn(UserController.class).deleteById(id)).toUri())
                        .with(csrf()))
                .andExpect(status().is(204));

        verify(service, times(1)).deleteById(id);
    }


}