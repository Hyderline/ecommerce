package com.simoneg.ecommerce.repositories;

import com.simoneg.ecommerce.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RolesRepository extends JpaRepository<Roles, UUID> {

    Optional<Roles> findByRoleName(String roleName);

}
