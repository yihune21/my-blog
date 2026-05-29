package com.blog.my_blog.mapper;

import com.blog.my_blog.dto.response.TagResponse;
import com.blog.my_blog.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    
    public TagResponse toResponse(Tag tag) {
        if (tag == null) {
            return null;
        }
        return new TagResponse(tag.getId(), tag.getName());
    }
}
