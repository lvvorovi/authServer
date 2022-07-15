package trackerAuth.user.mapper;

import trackerAuth.user.domain.UserEntity;
import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoResponse;
import trackerAuth.user.dto.UserDtoUpdateRequest;
import trackerAuth.user.scope.UserScope;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserModelMapperImplTest {

    @Autowired
    UserModelMapperImpl victim;

    private UserEntity userEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setPassword("password");
        entity.setScope(UserScope.READ);
        entity.setUsername("user@user.com");
        return entity;
    }

    private UserDtoResponse userResponseDto(UserEntity entity) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setScope(entity.getScope());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    private UserDtoCreateRequest createUserDto(UserEntity entity) {
        UserDtoCreateRequest dto = new UserDtoCreateRequest();
        dto.setPassword(entity.getPassword());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    private UserDtoUpdateRequest updateUserDto(UserEntity entity) {
        UserDtoUpdateRequest dto = new UserDtoUpdateRequest();
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Test
    void createDtoToEntity_returnEntity() {
        UserDtoCreateRequest userDto = createUserDto(userEntity());
        UserEntity mappedEntity = victim.dtoToEntity(userDto);
        assertEquals(userDto.getPassword(), mappedEntity.getPassword());
        assertEquals(userDto.getUsername(), mappedEntity.getUsername());
        assertNull(mappedEntity.getScope());
        assertNull(mappedEntity.getId());
    }

    @Test
    void updateDtoRequestToEntity_returnsEntity() {
        UserEntity entity = userEntity();
        UserDtoUpdateRequest updateRequest = updateUserDto(entity);
        UserEntity mappedEntity = victim.dtoToEntity(updateRequest);
        assertEquals(updateRequest.getId(), mappedEntity.getId());
        assertEquals(updateRequest.getUsername(), mappedEntity.getUsername());
        assertEquals(updateRequest.getPassword(), mappedEntity.getPassword());
        assertNull(mappedEntity.getScope());
    }

    @Test
    void entityToDto_returnsDto() {
        UserEntity entity = userEntity();
        UserDtoResponse responseDto = userResponseDto(entity);
        UserDtoResponse mappedDto = victim.entityToDto(entity);
        assertEquals(responseDto, mappedDto);
    }
}