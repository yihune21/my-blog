package com.blog.my_blog.service;

import com.blog.my_blog.dto.request.TagCreateRequest;
import com.blog.my_blog.dto.response.TagResponse;
import java.util.List;
import java.util.UUID;

public interface TagService {
    TagResponse createTag(TagCreateRequest request);
    TagResponse getTagById(UUID id);
    List<TagResponse> getAllTags();
    void deleteTag(UUID id);
}
