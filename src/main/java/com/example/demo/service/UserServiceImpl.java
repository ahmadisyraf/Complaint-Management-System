package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = mapToEntity(userRequestDto);

        RoleEntity userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userEntity.setRoles(Set.of(userRole));

        UserEntity savedUser = userRepository.save(userEntity);

        return mapToDto(savedUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public String deleteUser(Long id) {
        userRepository.deleteById(id);

        return "User deleted";
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(("User not found")));

        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());

        return mapToDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateCurrentUser(HttpServletRequest request, UserRequestDto userRequestDto) {
        Claims payload = jwtUtil.getPayload(jwtUtil.extractToken(request));

        UserEntity user = userRepository.findByEmail(payload.getSubject())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());

        return mapToDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUserProfile(HttpServletRequest request) {
        Claims payload = jwtUtil.getPayload(jwtUtil.extractToken(request));

        return userRepository.findByEmail(payload.getSubject())
                .map(this::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUserProfile(HttpServletRequest request) {
        Claims payload = jwtUtil.getPayload(jwtUtil.extractToken(request));

        userRepository.deleteByEmail(payload.getSubject());
    }

    private UserEntity mapToEntity(UserRequestDto userRequestDto) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        return userEntity;
    }

    private UserResponseDto mapToDto(UserEntity userEntity) {
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(userEntity.getId());
        userResponseDto.setUsername(userEntity.getUsername());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setCreatedAt(userEntity.getCreatedAt());
        userResponseDto.setUpdatedAt(userEntity.getUpdateAt());
        userResponseDto.setRoles(userEntity.getRoles());

        return userResponseDto;
    }

}
