package com.blog.my_blog.dto.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PostResponse(
    UUID id,
    String title,
    String slug,
    String content,
    String summary,
    String status,
    UUID authorId,
    String authorUsername,
    Instant createdAt,
    Instant updatedAt,
    Set<String> tagNames
) {}
