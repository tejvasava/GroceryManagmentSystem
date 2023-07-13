package com.grocery.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.main.core.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

}
