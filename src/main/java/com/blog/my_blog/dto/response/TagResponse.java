package com.blog.my_blog.dto.response;

import java.util.UUID;

public record TagResponse(
    UUID id,
    String name
) {}
