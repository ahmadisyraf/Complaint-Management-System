package com.example.demo.service;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;

public interface AuthService {
    AuthResponseDto authenticate(AuthRequestDto authRequestDto);
    AuthResponseDto refreshAccessToken(String refreshToken);
}
