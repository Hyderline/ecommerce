package com.simoneg.ecommerce.repositories;

import com.simoneg.ecommerce.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
}
