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
import com.example.cinema.uitl.TestParamsInitUtil;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static User user;
    private static UserRegistrationRequestDto userRegistrationRequestDto;
    private static UserRoleUpdateRequestDto userRoleUpdateRequestDto;
    private static UserResponseDto userResponseDto;
    private static UserWithRolesResponseDto userWithRolesResponseDto;
    private static Role userRole;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Logger logger;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void setUp() {
        user = new User();
        userRole = new Role();
        userRegistrationRequestDto = new UserRegistrationRequestDto();
        userRoleUpdateRequestDto = new UserRoleUpdateRequestDto();
        userResponseDto = new UserResponseDto();
        userWithRolesResponseDto = new UserWithRolesResponseDto();

        TestParamsInitUtil.initializeAllUserFields(user, userRole,
                userResponseDto, userRegistrationRequestDto,
                userRoleUpdateRequestDto, userWithRolesResponseDto);
    }

    @Test
    @DisplayName("Register new user and return response dto")
    void registerUser_WithValidDto_ReturnResponseDto() {
        Mockito.when(userRepository.findByEmail(userRegistrationRequestDto.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(userRegistrationRequestDto.getPassword()))
                .thenReturn("encodedPassword");
        Mockito.when(roleRepository.findByRoleName(Role.RoleName.USER))
                .thenReturn(userRole);
        Mockito.when(userMapper.toModel(userRegistrationRequestDto))
                .thenReturn(user);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userMapper.toDto(user))
                .thenReturn(userResponseDto);

        UserResponseDto actual = userService.register(userRegistrationRequestDto);
        UserResponseDto expected = userResponseDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw RegistrationException when trying to register with existing email")
    void registerUser_WithExistingEmail_ThrowRegistrationException() {
        Mockito.when(userRepository.findByEmail(userRegistrationRequestDto.getEmail()))
                .thenReturn(Optional.of(user));

        Assertions.assertThrows(RegistrationException.class,
                () -> userService.register(userRegistrationRequestDto));
    }

    @Test
    @DisplayName("Update user role and return updated response dto")
    void updateUserRole_WithValidId_ReturnUpdatedResponseDto() {
        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findByRoleNameIn(userRoleUpdateRequestDto.getRoleNames()))
                .thenReturn(Set.of(userRole));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userMapper.toDtoWithRoles(user))
                .thenReturn(userWithRolesResponseDto);

        UserWithRolesResponseDto actual = userService
                .updateUserRole(user.getId(), userRoleUpdateRequestDto);
        UserWithRolesResponseDto expected = userWithRolesResponseDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to update role for non-existing user")
    void updateUserRole_WithInvalidId_ThrowEntityNotFoundException() {
        Long invalidUserId = 99L;
        Mockito.when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.updateUserRole(invalidUserId, userRoleUpdateRequestDto));
    }
}
