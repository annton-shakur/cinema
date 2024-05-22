package com.example.cinema.dto.user;

import com.example.cinema.model.Role;
import java.util.Set;
import lombok.Data;

@Data
public class UserWithRolesResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role.RoleName> roleNames;
}
