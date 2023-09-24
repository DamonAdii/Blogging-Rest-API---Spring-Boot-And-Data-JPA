package com.blogging.Register.verification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepo extends JpaRepository<VerificationRequest, Integer>{
	
	VerificationRequest findByToken(String token);

}
