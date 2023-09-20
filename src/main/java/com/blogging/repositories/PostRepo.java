package com.blogging.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogging.entities.Category;
import com.blogging.entities.Post;
import com.blogging.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByPostTitleContaining(String postTitle);
	

}
