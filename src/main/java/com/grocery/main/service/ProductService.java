package com.grocery.main.service;

import org.springframework.data.domain.Page;

import com.grocery.main.dto.ProductDto;
import com.grocery.main.dto.ResponseVO;

public interface ProductService {

	ResponseVO<ProductDto> addEditProduct(ProductDto productDto);

	ProductDto getProductVOById(Long id);

	Page<ProductDto> findAllProduct(int pageNo, int pageSize);

}
