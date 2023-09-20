package com.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogging.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
