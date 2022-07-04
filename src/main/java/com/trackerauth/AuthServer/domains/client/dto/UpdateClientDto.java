package com.trackerauth.AuthServer.domains.client.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Validated
public class UpdateClientDto {

    @NotNull
    private String id;
    @NotNull
    private String name;

}
