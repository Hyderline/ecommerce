package com.simoneg.ecommerce.repositories;

import com.simoneg.ecommerce.model.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompaniesRepository extends JpaRepository<Companies, UUID> {

    Optional<Companies> findByCompanyName(String companyName);

}
