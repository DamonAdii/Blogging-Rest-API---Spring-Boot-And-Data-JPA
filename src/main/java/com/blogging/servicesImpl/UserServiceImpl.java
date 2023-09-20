package com.blogging.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.UserDto;
import com.blogging.repositories.UserRepo;
import com.blogging.services.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDato) {
			
			User user = this.dtoToUserEntiry(userDato);
			
			User savedUser = this.userRepo.save(user);
		
			return this.userToUserDTO(savedUser);
	}
	
	

	

	@Override
	public UserDto updateUser(UserDto userDto, int userID) {
	
		User user = this.userRepo.findById(userID).orElseThrow(()-> new ResourceNotFoundException("User","id",userID));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepo.save(user);
		
		return this.userToUserDTO(updatedUser);
	}
	
	
	@Override
	public UserDto getUserById(int userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		return this.userToUserDTO(user);
	}
	
	
	
	
	@Override
	public List<UserDto> getAllUser() {
		
		List<User> userList = this.userRepo.findAll();
		
		List<UserDto> userDtoList = userList.stream().map(user -> userToUserDTO(user)).collect(Collectors.toList());
		
		return userDtoList;
	}

	
	
	@Override
	public void deletUser(int userID) {
		
		User user = this.userRepo.findById(userID).orElseThrow(()-> new ResourceNotFoundException("User","id",userID));
		
		this.userRepo.delete(user);
		
	}
	
	
	
	
	public User dtoToUserEntiry(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
		
	}
	
	
	public UserDto userToUserDTO(User user) {
		
		UserDto userdto = this.modelMapper.map(user, UserDto.class);
		
		
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setAbout(user.getAbout());
//		userdto.setPassword(user.getPassword());
		
		return userdto;
		
	}






}
