package com.grocery.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.main.dto.OrderItemsDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.service.OrderItemsService;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

	@Autowired
	private OrderItemsService orderItemsService;
	
	@PostMapping("/addEditOrderItem")
	public  ResponseVO<OrderItemsDto> addEditOrderItems(@RequestBody OrderItemsDto orderItemsDto) {
		return orderItemsService.addEditOrderItems(orderItemsDto);
	}
	
}
