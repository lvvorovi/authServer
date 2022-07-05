package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.domains.client.dto.ClientResponseDto;
import com.trackerauth.AuthServer.domains.client.dto.CreateRequestClientDto;
import com.trackerauth.AuthServer.domains.client.dto.UpdateRequestClientDto;
import com.trackerauth.AuthServer.domains.client.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> findById(@PathVariable String id) {
        ClientResponseDto responseDto = service.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> save(@RequestBody CreateRequestClientDto dto) {
        ClientResponseDto responseDto = service.save(dto);
        addSelfLink(responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClientResponseDto> update(@Valid @RequestBody UpdateRequestClientDto dto) {
        ClientResponseDto responseDto = service.update(dto);
        addSelfLink(responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void addSelfLink(ClientResponseDto dto) {
        dto.add(linkTo(methodOn(ClientController.class).findById(dto.getId())).withSelfRel());
    }

}
