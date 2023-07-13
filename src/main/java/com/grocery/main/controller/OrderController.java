package com.grocery.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.main.dto.OrderDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/addEditOrder")
	public  ResponseVO<OrderDto> addEditOrder(@RequestBody OrderDto orderDto) {
		return orderService.addEditOrder(orderDto);
	}
	
	
	@GetMapping("/listOrder")
	public Page<OrderDto> getAllEmployee(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		return orderService.findAllOrders(pageNo, pageSize);
	}
	
	@GetMapping("/getOrder")
	public OrderDto getCustomerById(@RequestParam("id") Long id) {
		return orderService.getOrderVOById(id);
	}
}
