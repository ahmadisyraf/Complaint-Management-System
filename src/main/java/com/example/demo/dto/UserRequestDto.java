package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {
    @NotNull(message = "Username required")
    private String username;

    @Email(message = "Invalid email format")
    @NotNull(message = "Email required")
    private String email;

    @NotNull(message = "Password required")
    private String password;
}
