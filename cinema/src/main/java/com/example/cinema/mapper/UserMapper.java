package com.example.cinema.mapper;

import com.example.cinema.config.MapperConfig;
import com.example.cinema.dto.user.UserRegistrationRequestDto;
import com.example.cinema.dto.user.UserResponseDto;
import com.example.cinema.dto.user.UserWithRolesResponseDto;
import com.example.cinema.model.Role;
import com.example.cinema.model.User;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toDto(User user);

    @Mapping(target = "roleNames", ignore = true)
    UserWithRolesResponseDto toDtoWithRoles(User user);

    @AfterMapping
    default void setRoleNames(
            @MappingTarget UserWithRolesResponseDto responseDto,
            User user
    ) {
        if (user.getRoles() != null) {
            responseDto.setRoleNames(user.getRoles().stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toSet()));
        }
    }

    User toModel(UserRegistrationRequestDto requestDto);
}
