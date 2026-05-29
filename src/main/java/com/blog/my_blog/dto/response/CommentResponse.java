package com.blog.my_blog.dto.response;

import java.time.Instant;
import java.util.UUID;

public record CommentResponse(
    UUID id,
    String content,
    UUID postId,
    UUID authorId,
    String authorUsername,
    Instant createdAt
) {}
