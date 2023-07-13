package com.grocery.main.security;



import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (Objects.nonNull(object) && object instanceof JwtUser) {
			Long userId = ((JwtUser) object).getId();
			return Optional.of(userId);
		}

		return Optional.of(null);
	}

}