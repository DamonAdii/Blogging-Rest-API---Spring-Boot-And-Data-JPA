package com.blogging.utils;

public class EmailUtils {
	
	// method for registration verification account
	public static String getEmailMessage(String name, String host, String token) {
		
		return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" 
				+
				getVerificationUrl(host, token) + "\n\nThe support Team";
				
		
	}

	
	// method for forgot password
	public static String getForgotPasswordEmailMessage(String name, String host, String token) {
		
		return "Hello " + name + ",\n\nThere was a request to change your password! \n\n If you did not make this request then please ignore this email. \n\n Otherwise, please click this link to change your password:" 
				+
               getForgotpasswordUrl(host, token) + "\n\nThe support Team";
				
		
	}

	private static String getVerificationUrl(String host, String token) {

		return host + "/api/users?token=" + token;
	}

	private static String getForgotpasswordUrl(String host, String emailToken) {

		return host + "/api/users?token=" + emailToken;
	}

}
