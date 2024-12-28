package com.example.demo.controller;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.saveUser(userRequestDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponseDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(id, userRequestDto);
    }

    @DeleteMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping(value = "profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto getUserProfile(HttpServletRequest request) {
        return userService.getUserProfile(request);
    }

    @PatchMapping(value = "edit-profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto updateCurrentUser(@Valid @RequestBody UserRequestDto userRequestDto, HttpServletRequest request) {
        return userService.updateCurrentUser(request, userRequestDto);
    }

    @DeleteMapping(value = "delete-profile")
    public void deleteUserProfile(HttpServletRequest request) {
        userService.deleteUserProfile(request);
    }
}
