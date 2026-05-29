package com.blog.my_blog.controller;

import com.blog.my_blog.dto.request.LoginRequest;
import com.blog.my_blog.entity.User;
import com.blog.my_blog.exception.ResourceNotFoundException;
import com.blog.my_blog.repository.UserRepository;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + request.username()));

        if (!user.getPassword().equals(request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid password"));
        }

        // Mock token response. In production, return an actual JWT or set a session cookie.
        String dummyToken = "dummy-jwt-token-for-user-" + user.getId();
        return ResponseEntity.ok(Map.of("token", dummyToken, "role", user.getRole()));
    }
}
