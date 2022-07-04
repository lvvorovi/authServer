package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.domains.user.dto.CreateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
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
    public ResponseEntity<?> saveUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserResponseDto responseDto = userService.save(createUserDto);
        addSelfLink(responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    private void addSelfLink(UserResponseDto dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId().toString())).withSelfRel());
    }
}
