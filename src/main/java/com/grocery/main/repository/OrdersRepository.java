package com.grocery.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.main.core.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
