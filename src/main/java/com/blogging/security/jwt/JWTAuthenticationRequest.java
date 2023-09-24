package com.blogging.security.jwt;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {

	private String userName;
	private String password;
	
}
