package com.blog.my_blog.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
    @NotBlank(message = "Content is required")
    String content,

    @NotNull(message = "Post ID is required")
    UUID postId,

    @NotNull(message = "Author ID is required")
    UUID authorId
) {}
