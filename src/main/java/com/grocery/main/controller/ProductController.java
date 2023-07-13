package com.grocery.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.main.dto.ProductDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/addEditProduct")
	public  ResponseVO<ProductDto>  addEditCustomer(@RequestBody ProductDto productDto) {
		return productService.addEditProduct(productDto);
	}
	
	@GetMapping("/listProduct")
	public Page<ProductDto> getAllCategory(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		return productService.findAllProduct(pageNo, pageSize);
	}
	
	@GetMapping("/getProduct")
	public ProductDto getProductById(@RequestParam("id") Long id) {
		return productService.getProductVOById(id);
	}
}
