package com.blogging.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.payloads.CommentDto;
import com.blogging.services.CommentService;
import com.blogging.utils.ApiResponse;

@RestController
@RequestMapping("/api")
public class CommentController {

	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> addComment(
			
			@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId
			
			){
		
		CommentDto createdComment = this.commentService.createComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);
		
	}
	
	
	
	
	
	@DeleteMapping("/posts/comments/{commentId}")
	public ResponseEntity<ApiResponse> addComment(@PathVariable("commentId") Integer commentId){
		
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", true),HttpStatus.OK);
		
	}
	
}
