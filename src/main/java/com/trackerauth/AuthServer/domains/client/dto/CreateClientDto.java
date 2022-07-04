package com.trackerauth.AuthServer.domains.client.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Validated
@Data
public class CreateClientDto {

    @NotEmpty
    @Size(min = 3, max = 30, message = "size from 3 to 30")
    private String name;
    @NotEmpty
    @Size(min = 3, max = 30, message = "size from 3 to 30")
    private String secret;

}
