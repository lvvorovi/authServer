package trackerAuth.client.dto;

import lombok.Data;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
public class ClientDtoUpdateRequest {

    @NotBlank
    private String id;
    @NotBlank
    private String clientId;
    @NotBlank
    @Size(min = 3, max = 30, message = "size from 3 to 30")
    private String name;
    @NotBlank
    @Size(min = 3, max = 30, message = "size from 3 to 30")
    private String secret;
    @NotBlank
    private String redirectUri;
    @NotBlank
    private String scope;
    @NotNull
    private ClientAuthenticationMethod authenticationMethod;
    @NotNull
    private AuthorizationGrantType authorizationGrantType;

}
