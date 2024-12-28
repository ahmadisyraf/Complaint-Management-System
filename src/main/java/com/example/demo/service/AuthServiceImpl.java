package com.example.demo.service;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

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
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(authRequestDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid credential");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return mapToDto(token, refreshToken);
    }

    @Override
    public AuthResponseDto refreshAccessToken(String refreshToken) {

        if(jwtUtil.isTokenValid(refreshToken)) {
            String email = jwtUtil.getPayload(refreshToken).getSubject();

            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            String newAccessToken = jwtUtil.generateToken(email, user.getRoles());

            String newRefreshToken = jwtUtil.generateRefreshToken(email);

            return mapToDto(newAccessToken, newRefreshToken);

        } else  {
            throw new RuntimeException("Invalid token");
        }
    }

    public AuthResponseDto mapToDto(String token, String refreshToken) {
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);
        authResponseDto.setRefreshToken(refreshToken);
        return authResponseDto;
    }
}
