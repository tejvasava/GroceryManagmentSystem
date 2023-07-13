package com.grocery.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.main.core.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByEmail(String username);
	
	Optional<User> findFirstByEmail(String username);

	User findByFirstName(String username);

	

}
