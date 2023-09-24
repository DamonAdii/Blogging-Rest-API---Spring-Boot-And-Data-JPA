package com.blogging.servicesImpl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogging.Register.password.ForgotPasswordRepo;
import com.blogging.Register.password.ForgotPasswordRequest;
import com.blogging.Register.verification.VerificationRepo;
import com.blogging.Register.verification.VerificationRequest;
import com.blogging.email.EmailService;
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
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationRepo verificationRepo;
	
	@Autowired
	private EmailService emailtService;
	
	@Autowired
	private ForgotPasswordRepo forgotPasswordRepo;
	
	@Override
	public UserDto createUser(UserDto userDato) {
		
		   // first check that user email is already present or not
		
		   Optional<User> findByEmail = this.userRepo.findByEmail(userDato.getEmail());
		   
		   if(findByEmail.isPresent()) {
			 
			   throw new RuntimeException("Email is already in use !please try with anoyher");
		   }
		   else
		   {
			   
			   //step 2 : save the user in db
			    User user = this.dtoToUserEntiry(userDato);
				
				user.setPassword(passwordEncoder.encode(userDato.getPassword()));
				
				user.setEnabled(false);
				
				User savedUser = this.userRepo.save(user);
				
				
				//step 3 : genertae vaerification token
				
				var verficationUrl = new VerificationRequest(savedUser);
				
				//now save the verification token in vercation table
				VerificationRequest savedVerification = this.verificationRepo.save(verficationUrl);
				
				
				//sent confirmation link to confirm account
				this.emailtService.simpleMailMessage(userDato.getName(), userDato.getEmail(), savedVerification.getToken());
				
				
				return this.userToUserDTO(savedUser);
			   
		   }
			
			
	}
	
	
	
	@Override
	public String validateToken(String theToken) {
		 VerificationRequest token = verificationRepo.findByToken(theToken);
	        if(token == null){
	            return "Invalid verification token";
	        }
	        
	        User user = token.getUser();
	        
	        Calendar calendar = Calendar.getInstance();
	        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
	        	verificationRepo.delete(token);
	            return "Token already expired";
	        }
	        
	        user.setEnabled(true);
	        userRepo.save(user);
	        return "valid";
	}

	

	@Override
	public UserDto updateUser(UserDto userDto, int userID) {
	
		User user = this.userRepo.findById(userID).orElseThrow(()-> new ResourceNotFoundException("User","id",userID));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
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



	@Override
	public String forgotpassword(String email) {
		
		 Optional<User> findByEmail = this.userRepo.findByEmail(email);
		   
		   if(findByEmail.isPresent()) {
			 
			   User user = findByEmail.get();
			// now we have to generate a token for forgot password
			var forgotpasswordUrl = new ForgotPasswordRequest(user);
			   
			//now save the forgot password verification token in verification table
			ForgotPasswordRequest savedPasswordToken = this.forgotPasswordRepo.save(forgotpasswordUrl);
				
			//sent confirmation link to confirm account
			this.emailtService.simpleMailMessageForForgotpassword(user.getName(), user.getEmail(), savedPasswordToken.getToken());
			
			return "Please visit your mail account to change password";
		   }
		
		return "Email is not present !please try with anoyher";
	}






}
