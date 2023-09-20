package com.blogging.services;

import java.util.List;

import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;

public interface PostService {
	
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	public PostDto updatePost(PostDto postDto, Integer postId);
	
	public void deletePost(Integer postId);
	
	public PostDto getPostById(Integer postId);
	
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	public List<PostDto> getPostByCategoryId(Integer categoryId);
	
	public List<PostDto> getPostByUserId(Integer userId);
	
	public List<PostDto> searchPosts(String keyword);

}
