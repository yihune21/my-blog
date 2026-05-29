package com.blog.my_blog.service.impl;

import com.blog.my_blog.dto.request.UserCreateRequest;
import com.blog.my_blog.dto.response.UserResponse;
import com.blog.my_blog.entity.User;
import com.blog.my_blog.exception.ResourceNotFoundException;
import com.blog.my_blog.mapper.UserMapper;
import com.blog.my_blog.repository.UserRepository;
import com.blog.my_blog.service.UserService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
       
       private final UserRepository userRepository;
       private final UserMapper userMapper;

       public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
              this.userRepository = userRepository;
              this.userMapper = userMapper;
       }

       @Override
       @Transactional
       public UserResponse createUser(UserCreateRequest request) {
              if (userRepository.findByUsername(request.username()).isPresent()) {
                     throw new IllegalArgumentException("Username is already taken");
              }
              if (userRepository.findByEmail(request.email()).isPresent()) {
                     throw new IllegalArgumentException("Email is already taken");
              }

              User user = new User(
                  null,
                  request.username(),
                  request.email(),
                  request.password(), // In a real application, encrypt this!
                  request.role(),
                  null
              );

              User savedUser = userRepository.save(user);
              return userMapper.toResponse(savedUser);
       }

       @Override
       @Transactional(readOnly = true)
       public UserResponse getUserById(UUID id) {
              User user = userRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
              return userMapper.toResponse(user);
       }

       @Override
       @Transactional(readOnly = true)
       public UserResponse getUserByUsername(String username) {
              User user = userRepository.findByUsername(username)
                  .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
              return userMapper.toResponse(user);
       }

       @Override
       @Transactional(readOnly = true)
       public List<UserResponse> getAllUsers() {
              return userRepository.findAll().stream()
                  .map(userMapper::toResponse)
                  .collect(Collectors.toList());
       }

       @Override
       @Transactional
       public void deleteUser(UUID id) {
              if (!userRepository.existsById(id)) {
                     throw new ResourceNotFoundException("User not found with id: " + id);
              }
              userRepository.deleteById(id);
       }
}