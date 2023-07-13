package com.grocery.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.main.core.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

}
