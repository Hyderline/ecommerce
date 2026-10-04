package com.simoneg.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Roles {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "role_name", nullable = false, unique = true, length = 50)
    private String roleName;

    @Column(name = "requires_company", nullable = false)
    private boolean requiresCompany;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permissions> permissions = new HashSet<>();

    @PrePersist
    void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

}
