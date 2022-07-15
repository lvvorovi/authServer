package trackerAuth.user.mapper;

import trackerAuth.user.domain.UserEntity;
import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoResponse;
import trackerAuth.user.dto.UserDtoUpdateRequest;

public interface UserMapper {

    UserEntity dtoToEntity(UserDtoCreateRequest dto);

    UserEntity dtoToEntity(UserDtoUpdateRequest dto);

    UserDtoResponse entityToDto(UserEntity entity);

}
