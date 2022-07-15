package trackerAuth.user.dto;

import trackerAuth.user.scope.UserScope;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDtoResponse extends RepresentationModel<UserDtoResponse> {

    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private UserScope scope;
}
