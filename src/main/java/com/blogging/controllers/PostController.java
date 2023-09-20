package com.blogging.controllers;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogging.config.ApiConstants;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;
import com.blogging.services.FileService;
import com.blogging.services.PostService;
import com.blogging.utils.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable("userId") Integer userId,@PathVariable("catId") Integer catId){
		
		PostDto createPost = this.postService.createPost(postDto, userId, catId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	
	
	//get posts by user id
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable("userId") Integer userId){
		
		List<PostDto> posts = this.postService.getPostByUserId(userId);
		//System.out.println("user by :"+posts);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		
	}
	
	
	//get posts by category id id
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable("catId") Integer catId){
		
		List<PostDto> posts = this.postService.getPostByCategoryId(catId);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK); 
		
	}
	
	
	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	
	// delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true),HttpStatus.OK);
	}
	
	
	//get post by post id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		PostDto postDto = this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	
	//get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam( value = "pageNumber", defaultValue = ApiConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam( value = "pageSize", defaultValue = ApiConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam( value = "sortBy", defaultValue = ApiConstants.SORT_BY, required = false) String sortBy,
			@RequestParam( value = "sortDir", defaultValue = ApiConstants.SORT_DIR, required = false) String sortDir
			){
		
		return ResponseEntity.ok(this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir));
	}
	
	
	// search post by keyword
	
	@GetMapping("/posts/search/{title}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("title") String title){
		
		return ResponseEntity.ok(this.postService.searchPosts(title));
		
	}
	
	
	
	//upload image for blog
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile imageFile,
			@PathVariable("postId") Integer postId
			){
		
		try {
			
			PostDto post = this.postService.getPostById(postId);
			
			String imageName = this.fileService.uploadImage(path, imageFile);
			
			post.setImageName(imageName);
			
			PostDto updatedPost = this.postService.updatePost(post, postId);
			
			return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		 
		return new ResponseEntity("Post updation Failed",HttpStatus.BAD_REQUEST);
		
	}
	
	
	//method to serve image
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response)throws Exception {
		
		InputStream inputStream = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());
	}
	

}
