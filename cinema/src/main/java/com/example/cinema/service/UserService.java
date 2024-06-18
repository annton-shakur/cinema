package com.example.cinema.service;

import com.example.cinema.dto.user.UserRegistrationRequestDto;
import com.example.cinema.dto.user.UserResponseDto;
import com.example.cinema.dto.user.UserRoleUpdateRequestDto;
import com.example.cinema.dto.user.UserWithRolesResponseDto;

public interface UserService {
    UserResponseDto register(final UserRegistrationRequestDto requestDto);

    UserWithRolesResponseDto updateUserRole(final Long id,
                                            final UserRoleUpdateRequestDto requestDto);

    UserResponseDto findById(final Long id);
}
