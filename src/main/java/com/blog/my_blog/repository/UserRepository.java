package com.blog.my_blog.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.my_blog.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User , UUID> {
    List<User> updateUserRole(String role);
    List<User> DeleteUser(UUID id);
    List<User> login(String username,String password);
    void logout(String id);
} 