package com.trackerauth.AuthServer.domains.client.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Validated
@Data
public class CreateRequestClientDto {

    @NotBlank
    @Size(min = 3, max = 30, message = "size from 3 to 30")
    private String name;
    @NotBlank
    @Size(min = 3, max = 30, message = "size from 3 to 30")
    private String secret;

}
