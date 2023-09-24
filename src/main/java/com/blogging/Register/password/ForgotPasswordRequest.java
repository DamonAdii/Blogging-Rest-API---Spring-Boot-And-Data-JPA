package com.blogging.Register.password;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.blogging.entities.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "forgotpassword")
public class ForgotPasswordRequest {
	

	private static final int EXPIRATION_TIME = 15;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String token;
	
	private Date expirationTime;
	
	@OneToOne(targetEntity = User.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	
	public ForgotPasswordRequest(User user) {
		
		this.user = user;
		this.token = user.getEmail();
		this.expirationTime = this.getTokenExpirationTime();
	}


	public Date getTokenExpirationTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
		return new Date(calendar.getTime().getTime());
	}
	

}
