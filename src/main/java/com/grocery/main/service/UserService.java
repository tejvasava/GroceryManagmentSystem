package com.grocery.main.service;

import org.springframework.data.domain.Page;

import com.grocery.main.core.User;
import com.grocery.main.dto.UserDto;
import com.grocery.main.dto.ResponseVO;

public interface UserService {

	ResponseVO<UserDto> addEditEmployee(UserDto employeeDto);

	Page<UserDto> findAllEmployee(int pageNo, int pageSize);

	UserDto getEmployeeVOById(Long id);

	User getUserByUserName(String email);

	ResponseVO<UserDto> addEditCustomer(UserDto userDto); 

}
