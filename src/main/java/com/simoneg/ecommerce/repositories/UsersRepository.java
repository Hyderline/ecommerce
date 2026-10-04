package com.simoneg.ecommerce.repositories;

import com.simoneg.ecommerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID>, JpaSpecificationExecutor<Users> {
    Optional<Users> findByUsername(String username);
}
