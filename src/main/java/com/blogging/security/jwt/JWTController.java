package com.blogging.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class JWTController {
	
	
	private final JWTService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	@PostMapping
	public String getTokenForAuthenticateUser(@RequestBody JWTAuthenticationRequest authenticationRequest) {
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			
			return this.jwtService.getGeneratedToken(authenticationRequest.getUserName());
			
		}else
		{
			throw new UsernameNotFoundException("Invalid Credential!!!");
		}
		
		
		
	}
	

}
