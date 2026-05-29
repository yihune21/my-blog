package com.blog.my_blog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.my_blog.entity.Tag;

public interface TagsRepository extends JpaRepository<Tag,UUID>{
    
}
