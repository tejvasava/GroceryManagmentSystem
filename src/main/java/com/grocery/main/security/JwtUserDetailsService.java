package com.grocery.main.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grocery.main.core.User;
import com.grocery.main.core.UserRole;
import com.grocery.main.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findOneByEmail(username);

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return new JwtUser(user.getUserId(), user.getEmail(),user.getFirstName(),user.getPassword(),
				  mapRolesToAuthorities(user.getUserRole()), true, "");
		}
		throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
	}

	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserRole roles) {
		return Arrays.asList(new SimpleGrantedAuthority(roles.getRoleName()));
	}
}