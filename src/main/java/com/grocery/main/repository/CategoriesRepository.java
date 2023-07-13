package com.grocery.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.main.core.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long>  {

	Categories findFirstByCategoryNameAndCategoryIdNot(String categoryName,Long categoryId);

}
