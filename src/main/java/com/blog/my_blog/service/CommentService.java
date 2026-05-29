package com.blog.my_blog.service;

import com.blog.my_blog.dto.request.CommentCreateRequest;
import com.blog.my_blog.dto.response.CommentResponse;
import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentResponse createComment(CommentCreateRequest request);
    CommentResponse getCommentById(UUID id);
    List<CommentResponse> getCommentsByPost(UUID postId);
    void deleteComment(UUID id);
}
