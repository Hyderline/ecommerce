package com.simoneg.ecommerce.repositories;

import com.simoneg.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
