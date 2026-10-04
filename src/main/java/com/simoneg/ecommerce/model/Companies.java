package com.simoneg.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Companies {
    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "company_name", nullable = false, unique = true, length = 100)
    private String companyName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "partita_iva", nullable = false, unique = true, length = 20)
    private String partitaIva;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Instant updatedAt;

    @PrePersist
    void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

}
