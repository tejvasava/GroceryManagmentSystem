package com.grocery.main.service;

import org.springframework.data.domain.Page;

import com.grocery.main.core.UserRole;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.dto.UserRoleDto;

public interface UserRoleService {

	UserRole getUserRoleByRoleId(Long id);

	ResponseVO<Void> addEditRole(UserRoleDto role);

	UserRoleDto getUserVoByid(Long id);

	Page<UserRoleDto> findAllUsersRolesList(int pageNo, int pageSize);

}
