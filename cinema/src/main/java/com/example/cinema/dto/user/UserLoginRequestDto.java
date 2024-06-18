package com.example.cinema.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
}
