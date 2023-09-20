package com.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogging.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
