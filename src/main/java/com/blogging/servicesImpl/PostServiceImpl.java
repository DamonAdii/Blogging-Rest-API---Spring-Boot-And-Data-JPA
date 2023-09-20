package com.blogging.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogging.entities.Category;
import com.blogging.entities.Post;
import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.CategoryDto;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;
import com.blogging.repositories.CategoryRepo;
import com.blogging.repositories.PostRepo;
import com.blogging.repositories.UserRepo;
import com.blogging.services.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	

	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post savedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(savedPost, PostDto.class);
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		
		post.setPostTitle(postDto.getPostTitle());
		post.setImageName(postDto.getImageName());
		post.setPostDesciption(postDto.getPostDesciption());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}
	
	@Override
	public void deletePost(Integer postId) {
		
		this.postRepo.deleteById(postId);
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post posts = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		
		PostDto postDto = this.modelMapper.map(posts, PostDto.class);
		
		return postDto;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		//tiernary operation
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
//		if(sortDir.equalsIgnoreCase("asc")) {
//			
//			sort = Sort.by(sortBy).ascending();
//		}
//		else
//		{
//			sort = Sort.by(sortBy).descending();
//		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		 
		Page<Post> postPages = this.postRepo.findAll(p);
		
		List<Post> allPosts = postPages.getContent();
		
		List<PostDto> postDtoAll = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtoAll);
		postResponse.setPageNumber(postPages.getNumber());
		postResponse.setPageSize(postPages.getSize());
		postResponse.setTotalElements(postPages.getTotalElements());
		postResponse.setTotalPages(postPages.getTotalPages());
		postResponse.setLastPage(postPages.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategoryId(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postsDto = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postsDto;
	}

	
	@Override
	public List<PostDto> getPostByUserId(Integer userId) {
		
		List<PostDto> postsDto = null;
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		List<Post> getPostByUser = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = getPostByUser.stream().map((postDto)-> this.modelMapper.map(postDto, PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> postList = this.postRepo.findByPostTitleContaining(keyword);
		
		List<PostDto> postDtoList = postList.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtoList;
	}

	
	
//	@Override
//	public List<PostDto> searchPosts(String keyword) {
//		
//		List<Post> postList= this.postRepo.findByTitleContaining(keyword);
//		
//		System.out.println(postList);
//		
//		List<PostDto> postDtoList = postList.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		
//		return postDtoList;
//	}
	
}
