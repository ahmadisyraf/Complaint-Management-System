package com.example.demo.service;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        UserEntity user = userRepository.findByEmail(authRequestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with " + authRequestDto.getEmail() + " not exist"));

        if (!passwordEncoder.matches(authRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credential");
        }

        return mapToDto(jwtUtil.generateToken(user.getEmail(), user.getRoles()));
    }

    public AuthResponseDto mapToDto(String token) {
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);
        return authResponseDto;
    }
}
