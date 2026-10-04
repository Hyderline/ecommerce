package com.simoneg.ecommerce.repositories;

import com.simoneg.ecommerce.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID> {
}
