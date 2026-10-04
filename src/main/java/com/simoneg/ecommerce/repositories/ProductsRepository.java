package com.simoneg.ecommerce.repositories;

import com.simoneg.ecommerce.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<Products, UUID> {
}
