package com.blogging.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.blogging.constants.EmailConstants;
import com.blogging.utils.EmailUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
	
	private final JavaMailSender mailSender;
	//private final TemplateEngine templateEngine; 
	
    private String host = "http://localhost:8080";
    
    @Value("${spring.mail.username}")
    private String fromEmail;
	
	@Override
	public void simpleMailMessage(String userName, String toEmail, String token) {
		
		
		try {
			
			// step 1 : create a instance of a SimpleMailMessage
			SimpleMailMessage message = new SimpleMailMessage();
			
			// step 2 : set subject into a message
			message.setSubject(EmailConstants.NEW_USER_ACCOUNT_VERIFICATION);
			
			// step 3 : set from email you want to send
			message.setFrom(fromEmail);
			
			//step 4 : set To you want to send mail
			message.setTo(toEmail);
			
			//step 5 : set text message you want to send
			message.setText(EmailUtils.getEmailMessage(userName, host, token));
			
			// step 6 : now send the mail
			mailSender.send(message);
			
			
		} catch (Exception e) {
			 
			System.out.println(e.getMessage());
	        throw new RuntimeException(e.getMessage());
		}
		
		
	}

	@Override
	public void simpleMailMessageForForgotpassword(String userName, String toEmail, String emailToken) {
		
		try {
			
			// step 1 : create a instance of a SimpleMailMessage
			SimpleMailMessage message = new SimpleMailMessage();
			
			// step 2 : set subject into a message
			message.setSubject(EmailConstants.FORGOT_PASSWORD_ACCOUNT_VERIFICATION);
			
			// step 3 : set from email you want to send
			message.setFrom(fromEmail);
			
			//step 4 : set To you want to send mail
			message.setTo(toEmail);
			
			//step 5 : set text message you want to send
			message.setText(EmailUtils.getForgotPasswordEmailMessage(userName, host, emailToken));
			
			// step 6 : now send the mail
			mailSender.send(message);
			
			
		} catch (Exception e) {
			 
			System.out.println(e.getMessage());
	        throw new RuntimeException(e.getMessage());
		}
		
	}
	
	
	
	

}
