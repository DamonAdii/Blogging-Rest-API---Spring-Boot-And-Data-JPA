package com.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogging.entities.User;



public interface UserRepo extends JpaRepository<User, Integer>{

	
}
