package com.trackerauth.AuthServer.domains.client.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Validated
@Data
public class ClientResponseDto {

    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String secret;

}
