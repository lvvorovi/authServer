package trackerAuth.user.mapper;

import trackerAuth.user.domain.UserEntity;
import trackerAuth.user.dto.UserDtoCreateRequest;
import trackerAuth.user.dto.UserDtoResponse;
import trackerAuth.user.dto.UserDtoUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapperImpl implements UserMapper {

    private final ModelMapper mapper;

    public UserModelMapperImpl(ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    @Override
    public UserEntity dtoToEntity(UserDtoCreateRequest dto) {
        return mapper.map(dto, UserEntity.class);
    }

    @Override
    public UserEntity dtoToEntity(UserDtoUpdateRequest dto) {
        return mapper.map(dto, UserEntity.class);
    }

    @Override
    public UserDtoResponse entityToDto(UserEntity entity) {
        return mapper.map(entity, UserDtoResponse.class);
    }
}
