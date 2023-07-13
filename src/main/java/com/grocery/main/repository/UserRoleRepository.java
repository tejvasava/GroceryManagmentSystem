package com.grocery.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.main.core.UserRole;

@Repository
public interface UserRoleRepository  extends JpaRepository<UserRole, Long>{

	UserRole findByRoleName(String roleName);

	


}
