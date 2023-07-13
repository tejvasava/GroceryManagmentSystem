package com.grocery.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.main.core.Suppliers;

@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Long> {

}
