package com.example.cinema.controller;

import com.example.cinema.dto.user.UserResponseDto;
import com.example.cinema.dto.user.UserRoleUpdateRequestDto;
import com.example.cinema.dto.user.UserWithRolesResponseDto;
import com.example.cinema.model.User;
import com.example.cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final Logger logger;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserWithRolesResponseDto updateUserRole(
            @PathVariable final Long id,
            @RequestBody @Valid final UserRoleUpdateRequestDto requestDto
    ) {
        logger.info("updateUserRoles method was called for userId: {}", id);
        return userService.updateUserRole(id, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public UserResponseDto getAccountDetails(final Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        logger.info("getAccountDetails method was called for the next user: {}",
                user.getId());
        return userService.findById(user.getId());
    }
}
