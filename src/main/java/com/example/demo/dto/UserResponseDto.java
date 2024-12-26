package com.example.demo.dto;

import com.example.demo.entity.RoleEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<RoleEntity> roles;
}
