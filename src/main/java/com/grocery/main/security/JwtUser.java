package com.grocery.main.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails {

		private static final long serialVersionUID = 1L;

		private final Long id;
		private final String username;
		private final String name;
		private final String password;
		private final Collection<? extends GrantedAuthority> authorities;
		private final boolean enabled;
		private final String role;

		public JwtUser(Long id, String username, String name, String password,
				Collection<? extends GrantedAuthority> authorities, boolean enabled, String role) {
			this.id = id;
			this.username = username;
			this.name = name;
			this.password = password;
			this.authorities = authorities;
			this.enabled = enabled;
			this.role = role;
		}

		public Long getId() {
			return id;
		}

		@Override
		public String getUsername() {
			return username;
		}

		@JsonIgnore
		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@JsonIgnore
		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@JsonIgnore
		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		public String getName() {
			return name;
		}

		@JsonIgnore
		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		@Override
		public boolean isEnabled() {
			return enabled;
		}

		public String getRole() {
			return role;
		}
	}

