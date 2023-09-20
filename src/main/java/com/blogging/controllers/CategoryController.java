package com.blogging.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blogging.payloads.CategoryDto;
import com.blogging.services.CategoryService;
import com.blogging.utils.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	    //add category function
		@PostMapping("/")
		public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
			
			CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
			
			return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
		}
		
		
		//update user function
		@PutMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer categoryId){
			
			CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
			
			return ResponseEntity.ok(updatedCategory);	
			
		}
		
		
		//delete user function
		@DeleteMapping("/{categoryId}")
		public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){
			
			this.categoryService.deleteCategory(categoryId);
			
			return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true),HttpStatus.OK);
		}
		
		
		
		
		//get all user function
		@GetMapping("/")
		public ResponseEntity<List<CategoryDto>> getAllCategory(){
			
			return ResponseEntity.ok(this.categoryService.getAllCategory());
		}
		
		
		
		// get a single user by userId
		@GetMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryId){
			
			return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
		}

}
