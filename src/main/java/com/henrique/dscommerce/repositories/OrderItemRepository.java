package com.henrique.dscommerce.repositories;

import com.henrique.dscommerce.entities.OrderItem;
import com.henrique.dscommerce.entities.OrderItemPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPk> {
}
