package com.blogging.services;

import java.util.List;

import com.blogging.payloads.CategoryDto;

public interface CategoryService {
	
	//create category
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	//update category
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete category
	public void deleteCategory(Integer categoryId);
	
	//getById category
	public CategoryDto getCategoryById(Integer categoryId);
	
	//getAll category
	public List<CategoryDto> getAllCategory();

}
