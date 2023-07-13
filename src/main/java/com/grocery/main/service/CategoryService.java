package com.grocery.main.service;

import org.springframework.data.domain.Page;

import com.grocery.main.dto.CategoriesDto;
import com.grocery.main.dto.ResponseVO;

public interface CategoryService {

	ResponseVO<CategoriesDto> addEditCategory(CategoriesDto categoriesDto);

	Page<CategoriesDto> findAllCategory(int pageNo, int pageSize);

	CategoriesDto getCaegoryVOById(Long id);

}
