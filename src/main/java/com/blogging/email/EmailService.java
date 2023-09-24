package com.blogging.email;

public interface EmailService {
	
	void simpleMailMessage(String userName,String toEmail,String token);
	void simpleMailMessageForForgotpassword(String userName,String toEmail,String emailToken);

}
