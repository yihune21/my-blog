package com.blog.my_blog.service;

import com.blog.my_blog.dto.request.UserCreateRequest;
import com.blog.my_blog.dto.response.UserResponse;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserById(UUID id);
    UserResponse getUserByUsername(String username);
    List<UserResponse> getAllUsers();
    void deleteUser(UUID id);
}
