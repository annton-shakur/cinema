package com.example.cinema.controller;

import com.example.cinema.dto.user.UserRoleUpdateRequestDto;
import com.example.cinema.dto.user.UserWithRolesResponseDto;
import com.example.cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    public UserWithRolesResponseDto updateUserRole(
            @PathVariable final Long id,
            @RequestBody @Valid final UserRoleUpdateRequestDto requestDto
    ) {
        return userService.updateUserRole(id, requestDto);
    }
}
