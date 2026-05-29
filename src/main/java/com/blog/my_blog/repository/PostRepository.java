package com.blog.my_blog.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.my_blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,UUID> {
    List<Post> findPostById(UUID id);
    List<Post> updateTitle(String title);
    List<Post> findPostByAuthor(UUID author_id);
}
