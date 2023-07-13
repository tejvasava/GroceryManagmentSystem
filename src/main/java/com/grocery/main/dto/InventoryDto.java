package com.grocery.main.dto;

import lombok.Data;

@Data
public class InventoryDto {

	private Long inventoryId;

	//private Products products;
	
	private Long productId;

	private Long stockQuantity;
}
