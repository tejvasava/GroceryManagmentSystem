package com.grocery.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ProductDto {

	private Long productId;
	
	private String productName;
	
	private CategoriesDto categories;
	 
	private Long price;
		
	private Long quantity;
}
