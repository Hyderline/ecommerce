package com.simoneg.ecommerce.repositories;

import com.simoneg.ecommerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID>, JpaSpecificationExecutor<Users> {
    Optional<Users> findByUsername(String username);

    void deleteByUsername(String username);

    @Query("SELECT r.roleName FROM Users u JOIN u.role r WHERE u.username = :username")
    Optional<String> findRoleByUsername(@Param("username") String username);

}
