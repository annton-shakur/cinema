package com.example.cinema.controller;

import com.example.cinema.dto.user.UserLoginRequestDto;
import com.example.cinema.dto.user.UserLoginResponseDto;
import com.example.cinema.dto.user.UserRegistrationRequestDto;
import com.example.cinema.dto.user.UserResponseDto;
import com.example.cinema.exception.AuthenticationException;
import com.example.cinema.exception.RegistrationException;
import com.example.cinema.security.AuthenticationService;
import com.example.cinema.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management",
        description = "Endpoints for authentication and authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final Logger logger;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user",
            description = "Add a new user with USER roleName to database")
    public UserResponseDto register(
            @RequestBody @Valid final UserRegistrationRequestDto requestDto
    )
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    @Operation(summary = "Login to a user account",
            description = "Get a JWT token to a certain account")
    public UserLoginResponseDto login(@RequestBody @Valid final UserLoginRequestDto requestDto)
            throws AuthenticationException {
        logger.info("The login method was called for the next user: {}",
                requestDto.getUsername());
        return authenticationService.authenticate(requestDto);
    }
}
