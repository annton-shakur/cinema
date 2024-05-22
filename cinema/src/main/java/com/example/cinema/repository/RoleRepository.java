package com.example.cinema.repository;

import com.example.cinema.model.Role;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(final Role.RoleName roleName);

    Set<Role> findByRoleNameIn(final Set<Role.RoleName> roleNames);
}
