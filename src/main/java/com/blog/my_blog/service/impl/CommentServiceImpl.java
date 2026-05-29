package com.blog.my_blog.service.impl;

import com.blog.my_blog.dto.request.CommentCreateRequest;
import com.blog.my_blog.dto.response.CommentResponse;
import com.blog.my_blog.entity.Comment;
import com.blog.my_blog.entity.Post;
import com.blog.my_blog.entity.User;
import com.blog.my_blog.exception.ResourceNotFoundException;
import com.blog.my_blog.mapper.CommentMapper;
import com.blog.my_blog.repository.CommentRepository;
import com.blog.my_blog.repository.PostRepository;
import com.blog.my_blog.repository.UserRepository;
import com.blog.my_blog.service.CommentService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
                              UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public CommentResponse createComment(CommentCreateRequest request) {
        Post post = postRepository.findById(request.postId())
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + request.postId()));
        User author = userRepository.findById(request.authorId())
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.authorId()));

        Comment comment = new Comment(
            null,
            request.content(),
            post,
            author,
            null
        );

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toResponse(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponse getCommentById(UUID id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        return commentMapper.toResponse(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPost(UUID postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        return commentRepository.findByPostId(postId).stream()
            .map(commentMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteComment(UUID id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }
}
