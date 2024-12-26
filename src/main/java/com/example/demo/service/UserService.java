package com.example.demo.service;


import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequestDto);

    Optional<UserResponseDto> getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    String deleteUser(Long id);

    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);

    UserResponseDto updateCurrentUser(HttpServletRequest request, UserRequestDto userRequestDto);

    UserResponseDto getUserProfile(HttpServletRequest request);

    String deleteUserProfile(HttpServletRequest request);
}
