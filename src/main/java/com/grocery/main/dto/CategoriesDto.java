package com.grocery.main.dto;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonInclude(Include.NON_NULL)
public class CategoriesDto {

	private Long categoryId;

	private String categoryName;
}
