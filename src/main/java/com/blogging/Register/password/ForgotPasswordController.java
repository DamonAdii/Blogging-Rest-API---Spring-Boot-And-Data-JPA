package com.blogging.Register.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.services.UserService;


@RestController
@RequestMapping("/api")
public class ForgotPasswordController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotpassword(@RequestParam("email") String email) {
		
		return new ResponseEntity<String>(this.userService.forgotpassword(email), HttpStatus.OK);
	}
	

}
