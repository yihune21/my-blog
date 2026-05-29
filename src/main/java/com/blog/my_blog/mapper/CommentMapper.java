package com.blog.my_blog.mapper;

import com.blog.my_blog.dto.response.CommentResponse;
import com.blog.my_blog.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponse toResponse(Comment comment) {
        if (comment == null) {
            return null;
        }

        return new CommentResponse(
            comment.getId(),
            comment.getContent(),
            comment.getPost() != null ? comment.getPost().getId() : null,
            comment.getAuthor() != null ? comment.getAuthor().getId() : null,
            comment.getAuthor() != null ? comment.getAuthor().getUsername() : null,
            comment.getCreatedAt()
        );
    }
}
