package com.trackerauth.AuthServer.domains.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientDtoResponse extends RepresentationModel<ClientDtoResponse> {

    private String id;
    private String name;
    @JsonIgnore
    private String secret;

}
