package com.blog.my_blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagCreateRequest(
    @NotBlank(message = "Tag name is required")
    @Size(max = 64, message = "Tag name cannot exceed 64 characters")
    String name
) {}
