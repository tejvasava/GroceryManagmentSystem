package com.grocery.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.main.dto.CategoriesDto;
import com.grocery.main.dto.ResponseVO;
import com.grocery.main.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/addEditCategory")
	public  ResponseVO<CategoriesDto>  addEditCategory(@RequestBody CategoriesDto categoriesDto) {
		return categoryService.addEditCategory(categoriesDto);
	}
	
	@GetMapping("/listCategory")
	public Page<CategoriesDto> getAllCategory(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		return categoryService.findAllCategory(pageNo, pageSize);
	}
	
	@GetMapping("/getCategory")
	public CategoriesDto getCategoryById(@RequestParam("id") Long id) {
		return categoryService.getCaegoryVOById(id);
	}
	
	
}
