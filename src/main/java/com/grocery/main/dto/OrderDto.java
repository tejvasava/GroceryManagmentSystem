package com.grocery.main.dto;

import lombok.Data;

@Data
public class OrderDto {

	private Long orderId;
	
	private Long userId;
	//private Customers customers;
	
	private String orderDate;

	private Long totalAmount;
}
