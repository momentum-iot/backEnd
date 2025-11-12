package com.example.gymadmin.controller;

import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymadmin.dto.AuthResponse;
import com.example.gymadmin.dto.LoginRequest;
import com.example.gymadmin.dto.LogoutRequest;
import com.example.gymadmin.model.User;
import com.example.gymadmin.service.AuthService;

import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Endpoints for user registration and login")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        try {
            AuthResponse registeredUser = authService.register(user);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            AuthResponse response = authService.refresh(refreshToken);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(authService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<User> user = authService.getById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProfile() {
        try {
            User currentUser = authService.getCurrentUser();
            return ResponseEntity.ok(currentUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok("Logout successful");
    }

}
