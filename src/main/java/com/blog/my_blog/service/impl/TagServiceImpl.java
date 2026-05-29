package com.blog.my_blog.service.impl;

import com.blog.my_blog.dto.request.TagCreateRequest;
import com.blog.my_blog.dto.response.TagResponse;
import com.blog.my_blog.entity.Tag;
import com.blog.my_blog.exception.ResourceNotFoundException;
import com.blog.my_blog.mapper.TagMapper;
import com.blog.my_blog.repository.TagsRepository;
import com.blog.my_blog.service.TagService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

    private final TagsRepository tagsRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagsRepository tagsRepository, TagMapper tagMapper) {
        this.tagsRepository = tagsRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
    public TagResponse createTag(TagCreateRequest request) {
        if (tagsRepository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("Tag already exists with name: " + request.name());
        }
        Tag tag = new Tag(null, request.name());
        Tag savedTag = tagsRepository.save(tag);
        return tagMapper.toResponse(savedTag);
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponse getTagById(UUID id) {
        Tag tag = tagsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
        return tagMapper.toResponse(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getAllTags() {
        return tagsRepository.findAll().stream()
            .map(tagMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTag(UUID id) {
        if (!tagsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag not found with id: " + id);
        }
        tagsRepository.deleteById(id);
    }
}
