package com.grocery.main.service;

import com.grocery.main.dto.OrderItemsDto;
import com.grocery.main.dto.ResponseVO;

public interface OrderItemsService {

	ResponseVO<OrderItemsDto> addEditOrderItems(OrderItemsDto orderItemsDto);

}
