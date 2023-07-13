package com.grocery.main.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grocery.main.core.User;
import com.grocery.main.core.UserRole;
import com.grocery.main.repository.UserRepository;
import com.grocery.main.repository.UserRoleRepository;

@Component
public class DefaultDataLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	@Override
	public void run(String... args) throws Exception {

		
		if (userRoleRepository.count() == 0) {
			UserRole userRole=new UserRole();
			userRole.setRoleName("Admin");
			userRoleRepository.save(userRole);
			
		}
		
		if (userRepository.count() == 0) {
			
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setAddress("Ahmedabad");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setPassword("$2a$10$/n8LR3rrO/bwKhQjNdNfqu5UJKjRIXN7akXW2t.00.81kFVywEc8K");
			user.setPhoneNumber("1011456541");
			Optional<UserRole> userRole = userRoleRepository.findById(1L);
			user.setUserRole(userRole.get());
			userRepository.save(user);
		}
		
		

	}

}
