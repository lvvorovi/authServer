package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.domains.user.dto.UserDtoCreateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserDtoUpdateRequest;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable String id) {
        UserResponseDto responseDto = userService.findById(id);
        addSelfLink(responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDtoCreateRequest userDtoCreateRequest) {
        UserResponseDto responseDto = userService.save(userDtoCreateRequest);
        addSelfLink(responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(@Valid @RequestBody UserDtoUpdateRequest userDtoUpdateRequest) {
        UserResponseDto responseDto = userService.update(userDtoUpdateRequest);
        addSelfLink(responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@NotBlank @PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void addSelfLink(UserResponseDto dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel());
    }
}
