package com.simoneg.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "permissions")
public class Permissions {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "permission_name", nullable = false, unique = true, length = 100)
    private String permissionName;

    @PrePersist
    void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

}
