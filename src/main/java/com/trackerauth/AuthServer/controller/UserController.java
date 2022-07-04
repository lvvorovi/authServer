package com.trackerauth.AuthServer.controller;

import com.trackerauth.AuthServer.domains.user.dto.CreateUserDto;
import com.trackerauth.AuthServer.domains.user.dto.UserResponseDto;
import com.trackerauth.AuthServer.domains.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> userResponseDtoList = List.of(userService.findByUserName("user"));

        return ResponseEntity.ok(userResponseDtoList);

    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody CreateUserDto dto) {
        UserResponseDto responseDto = userService.save(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
