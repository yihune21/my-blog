package com.blog.my_blog.service.impl;

import com.blog.my_blog.dto.request.PostCreateRequest;
import com.blog.my_blog.dto.response.PostResponse;
import com.blog.my_blog.entity.Post;
import com.blog.my_blog.entity.Tag;
import com.blog.my_blog.entity.User;
import com.blog.my_blog.exception.ResourceNotFoundException;
import com.blog.my_blog.mapper.PostMapper;
import com.blog.my_blog.repository.PostRepository;
import com.blog.my_blog.repository.TagsRepository;
import com.blog.my_blog.repository.UserRepository;
import com.blog.my_blog.service.PostService;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagsRepository tagsRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
                           TagsRepository tagsRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagsRepository = tagsRepository;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        User author = userRepository.findById(request.authorId())
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.authorId()));

        String slug = generateSlug(request.title());

        Post post = new Post(
            null,
            request.title(),
            slug,
            request.content(),
            request.summary(),
            request.status(),
            author,
            null,
            null
        );

        if (request.tagNames() != null) {
            Set<Tag> tags = new HashSet<>();
            for (String name : request.tagNames()) {
                Tag tag = tagsRepository.findByName(name)
                    .orElseGet(() -> tagsRepository.save(new Tag(null, name)));
                tags.add(tag);
            }
            post.setTags(tags);
        }

        Post savedPost = postRepository.save(post);
        return postMapper.toResponse(savedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(UUID id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return postMapper.toResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
            .map(postMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByAuthor(UUID authorId) {
        return postRepository.findByAuthorId(authorId).stream()
            .map(postMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostResponse updatePost(UUID id, PostCreateRequest request) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        post.setTitle(request.title());
        post.setSlug(generateSlug(request.title()));
        post.setContent(request.content());
        post.setSummary(request.summary());
        post.setStatus(request.status());
        post.setUpdatedAt(Instant.now());

        if (request.tagNames() != null) {
            Set<Tag> tags = new HashSet<>();
            for (String name : request.tagNames()) {
                Tag tag = tagsRepository.findByName(name)
                    .orElseGet(() -> tagsRepository.save(new Tag(null, name)));
                tags.add(tag);
            }
            post.setTags(tags);
        }

        Post updatedPost = postRepository.save(post);
        return postMapper.toResponse(updatedPost);
    }

    @Override
    @Transactional
    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    private String generateSlug(String title) {
        if (title == null) return "";
        return title.toLowerCase()
            .replaceAll("[^a-z0-9\\s]", "")
            .replaceAll("\\s+", "-");
    }
}
