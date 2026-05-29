package com.blog.my_blog.service;

import com.blog.my_blog.dto.request.PostCreateRequest;
import com.blog.my_blog.dto.response.PostResponse;
import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponse createPost(PostCreateRequest request);
    PostResponse getPostById(UUID id);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByAuthor(UUID authorId);
    PostResponse updatePost(UUID id, PostCreateRequest request);
    void deletePost(UUID id);
}
