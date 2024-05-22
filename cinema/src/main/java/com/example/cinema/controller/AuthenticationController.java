package com.example.cinema.controller;

import com.example.cinema.dto.user.UserLoginRequestDto;
import com.example.cinema.dto.user.UserLoginResponseDto;
import com.example.cinema.dto.user.UserRegistrationRequestDto;
import com.example.cinema.dto.user.UserResponseDto;
import com.example.cinema.exception.AuthenticationException;
import com.example.cinema.exception.RegistrationException;
import com.example.cinema.security.AuthenticationService;
import com.example.cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(
            @RequestBody @Valid final UserRegistrationRequestDto requestDto
    )
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid final UserLoginRequestDto requestDto)
            throws AuthenticationException {
        return authenticationService.authenticate(requestDto);
    }
}
