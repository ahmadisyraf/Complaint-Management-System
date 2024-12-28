package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenRequestDto {
    @NotNull(message = "Refresh token required")
    private String refreshToken;
}
