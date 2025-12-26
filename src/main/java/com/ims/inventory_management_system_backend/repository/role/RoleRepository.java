package com.ims.inventory_management_system_backend.repository.role;

import com.ims.inventory_management_system_backend.entities.role.Role;
import com.ims.inventory_management_system_backend.entities.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
