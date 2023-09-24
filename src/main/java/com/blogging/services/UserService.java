package com.blogging.services;

import java.util.List;

import com.blogging.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	public String validateToken(String theToken);
	UserDto updateUser(UserDto userDto,int userID);
	UserDto getUserById(int userId);
	List<UserDto> getAllUser();
	void deletUser(int userID);

}
