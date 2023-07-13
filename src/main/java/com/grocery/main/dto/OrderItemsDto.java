package com.grocery.main.dto;

import lombok.Data;

@Data
public class OrderItemsDto {

	private Long orderItemId;

	//private Long orderId;

	private Long productId;
	
	private Long quantity;
	
	//private Long price;
}
