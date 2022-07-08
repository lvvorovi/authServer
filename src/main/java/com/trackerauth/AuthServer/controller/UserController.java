package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoResponse;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> findById(@PathVariable String id) {
        UserDtoResponse responseDto = service.findById(id);
        addSelfLink(responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<UserDtoResponse> save(@Valid @RequestBody UserDtoCreateRequest userDtoCreateRequest) {
        UserDtoResponse responseDto = service.save(userDtoCreateRequest);
        addSelfLink(responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDtoResponse> update(@Valid @RequestBody UserDtoUpdateRequest userDtoUpdateRequest) {
        UserDtoResponse responseDto = service.update(userDtoUpdateRequest);
        addSelfLink(responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@NotBlank @PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void addSelfLink(UserDtoResponse dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel());
    }
}
