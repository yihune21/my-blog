package com.blog.my_blog.mapper;

import com.blog.my_blog.dto.response.PostResponse;
import com.blog.my_blog.entity.Post;
import com.blog.my_blog.entity.Tag;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponse toResponse(Post post) {
        if (post == null) {
            return null;
        }

        Set<String> tagNames = post.getTags() != null 
            ? post.getTags().stream().map(Tag::getName).collect(Collectors.toSet())
            : Set.of();

        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getSlug(),
            post.getContent(),
            post.getSummary(),
            post.getStatus(),
            post.getAuthor() != null ? post.getAuthor().getId() : null,
            post.getAuthor() != null ? post.getAuthor().getUsername() : null,
            post.getCreatedAt(),
            post.getUpdatedAt(),
            tagNames
        );
    }
}
