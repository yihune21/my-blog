package com.blog.my_blog.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.my_blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,UUID>{
       List<Comment> findByPostId(UUID post_id); 
}
