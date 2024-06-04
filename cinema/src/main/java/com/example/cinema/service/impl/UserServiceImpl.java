package com.example.cinema.service.impl;

import com.example.cinema.dto.user.UserRegistrationRequestDto;
import com.example.cinema.dto.user.UserResponseDto;
import com.example.cinema.dto.user.UserRoleUpdateRequestDto;
import com.example.cinema.dto.user.UserWithRolesResponseDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.exception.RegistrationException;
import com.example.cinema.mapper.UserMapper;
import com.example.cinema.model.Role;
import com.example.cinema.model.User;
import com.example.cinema.repository.RoleRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String CANNOT_FIND_USER_BY_ID_MSG = "Cannot find user by id: ";
    private static final String EMAIL_FAILS_MSG = "Cannot register user with the provider email: ";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger;

    @Override
    public UserResponseDto register(final UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            logger.error(new RegistrationException(EMAIL_FAILS_MSG + requestDto.getEmail()));
            throw new RegistrationException(
                    EMAIL_FAILS_MSG + requestDto.getEmail()
            );
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role userRole = roleRepository.findByRoleName(Role.RoleName.USER);
        User user = userMapper.toModel(requestDto);
        user.setRoles(Set.of(userRole));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserWithRolesResponseDto updateUserRole(final Long id,
                                                   final UserRoleUpdateRequestDto requestDto
    ) {
        User user = userRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(new EntityNotFoundException(CANNOT_FIND_USER_BY_ID_MSG + id));
                    return new EntityNotFoundException(CANNOT_FIND_USER_BY_ID_MSG + id);
                }
        );
        Set<Role> existingRoles = roleRepository.findByRoleNameIn(requestDto.getRoleNames());
        user.setRoles(existingRoles);

        User updatedUser = userRepository.save(user);
        return userMapper.toDtoWithRoles(updatedUser);
    }
}
