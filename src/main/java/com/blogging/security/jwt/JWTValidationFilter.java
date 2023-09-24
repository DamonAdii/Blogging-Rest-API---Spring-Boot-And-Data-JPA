package com.blogging.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blogging.security.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTValidationFilter extends OncePerRequestFilter{

	
	
	private final JWTService jwtService;
	private final CustomUserDetailsService detailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = null;
		String username = null;
		
		String authHeader = request.getHeader("Authorization");
		
		System.out.println("token is :"+authHeader);
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			
			token  = authHeader.substring(7);
			
			username = this.jwtService.extractUsernameFromToken(token);
		
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.detailsService.loadUserByUsername(username);
			
			 if(jwtService.validateToken(token, userDetails)) {
	                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
			
		}
		
		filterChain.doFilter(request,response);
		
	}

}
