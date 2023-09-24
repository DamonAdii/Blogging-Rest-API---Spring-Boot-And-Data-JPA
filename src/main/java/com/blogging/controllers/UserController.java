package com.blogging.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.ApiResponse.HttpResponse;
import com.blogging.Register.verification.VerificationRepo;
import com.blogging.Register.verification.VerificationRequest;
import com.blogging.payloads.UserDto;
import com.blogging.services.UserService;
import com.blogging.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VerificationRepo verificationRepo;
	
	//signup or register user function
	@PostMapping("/signup")
	public ResponseEntity<HttpResponse> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto = this.userService.createUser(userDto);
		
		return ResponseEntity.created(URI.create("")).body(
				HttpResponse.builder()
				.timeStamp(LocalDateTime.now().toString())
				.data(Map.of("user",createUserDto))
				.message("Success!  Please, check your email for to complete your registration")
				.status(HttpStatus.CREATED)
				.statusCode(HttpStatus.CREATED.value())
				.build()
				);
	}
	
	
	@GetMapping
	public String loginUser(@RequestParam("token") String token) {
		
		//first validate token
		
		VerificationRequest vierifedToken = this.verificationRepo.findByToken(token);
		
		if(vierifedToken.getUser().isEnabled()) {
			
			return "This account has already been verified, please, login.";
		}
			
		String validateToken = this.userService.validateToken(token);
		
		if (validateToken.equalsIgnoreCase("valid")){
	            return "Email verified successfully. Now you can login to your account";
	        }
	       
		return "Invalid verification token";

	}
	
	
	
	//update user function
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
		
		UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
		
		return ResponseEntity.ok(updatedUserDto);	
		
	}
	
	
	//delete user function
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId){
		
		this.userService.deletUser(userId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
	
	
	
	
	//get all user function
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	
	
	
	// get a single user by userId
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId){
		
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	

}
