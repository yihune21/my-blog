package com.blog.my_blog.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
    UUID id,
    String username,
    String email,
    String role,
    Instant createdAt
) {}
