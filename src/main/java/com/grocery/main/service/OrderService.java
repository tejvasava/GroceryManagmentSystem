package com.grocery.main.service;

import org.springframework.data.domain.Page;

import com.grocery.main.dto.OrderDto;
import com.grocery.main.dto.ResponseVO;

public interface OrderService {

	ResponseVO<OrderDto> addEditOrder(OrderDto orderDto);

	Page<OrderDto> findAllOrders(int pageNo, int pageSize);

	OrderDto getOrderVOById(Long id);


}
