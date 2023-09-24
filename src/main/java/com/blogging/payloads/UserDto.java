package com.blogging.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	
	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "Username minimum length required is 4 charecters")
	private String name;
	
	@Email(message = "Email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min = 8, max = 30, message = "Password must be in between >8 and <=30 charecters")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Minimum eight characters, at least one uppercase letter, one lowercase letter and one number:")
	private String password;
	
	@NotEmpty
	private String about;
	
	private String roles;
	

}
