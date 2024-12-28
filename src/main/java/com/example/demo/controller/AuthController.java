package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "sign-in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AuthResponseDto authenticateUser(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return authService.authenticate(authRequestDto);
    }

    @PostMapping(value = "sign-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.saveUser(userRequestDto);
    }

    @PostMapping(value = "refresh-token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponseDto refreshAccessToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return authService.refreshAccessToken(refreshTokenRequestDto.getRefreshToken());
    }
}
