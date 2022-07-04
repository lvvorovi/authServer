package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateClientDto;
import com.trackerauth.AuthServer.domains.client.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientResponseDto> save(@Valid @RequestBody CreateClientDto dto) {
        ClientResponseDto responseDto = service.save(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
