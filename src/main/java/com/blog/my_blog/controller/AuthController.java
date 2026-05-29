package com.blog.my_blog.controller;

import com.blog.my_blog.dto.request.LoginRequest;
import com.blog.my_blog.entity.User;
import com.blog.my_blog.exception.ResourceNotFoundException;
import com.blog.my_blog.repository.UserRepository;
import com.blog.my_blog.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + request.username()));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole());
        return ResponseEntity.ok(Map.of("token", token, "role", user.getRole()));
    }
}
