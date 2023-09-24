package com.blogging.Register.password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordRequest, Integer>{

	ForgotPasswordRepo findByToken(String tokenemail);
}
