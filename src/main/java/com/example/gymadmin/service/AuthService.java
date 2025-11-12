package com.example.gymadmin.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.gymadmin.dto.AuthResponse;
import com.example.gymadmin.dto.LoginRequest;
import com.example.gymadmin.model.Role;
import com.example.gymadmin.model.User;
import com.example.gymadmin.repo.UserRepo;
import com.example.gymadmin.security.JwtTokenService;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthService(UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    public AuthResponse register(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepo.save(user);

        String accessToken = jwtTokenService.generateToken(saved);
        String refreshToken = jwtTokenService.generateRefreshToken(saved);

        saved.setRefreshToken(refreshToken);
        userRepo.save(saved);

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtTokenService.getExpirationSeconds(),
                saved);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String accessToken = jwtTokenService.generateToken(user);
        String refreshToken = jwtTokenService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepo.save(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtTokenService.getExpirationSeconds(),
                user);
    }

    public void logout(String refreshToken) {
    if (refreshToken == null || refreshToken.isEmpty()) {
        throw new IllegalArgumentException("Refresh token is required");
    }

    if (!jwtTokenService.isTokenValid(refreshToken)) {
        throw new IllegalArgumentException("Invalid or expired refresh token");
    }

    String email = jwtTokenService.extractEmail(refreshToken);

    User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    user.setRefreshToken(null);
    userRepo.save(user);
}

    public AuthResponse refresh(String refreshToken) {

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is required");
        }

        if (!jwtTokenService.isTokenValid(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        String email = jwtTokenService.extractEmail(refreshToken);

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String newAccessToken = jwtTokenService.generateToken(user);
        String newRefreshToken = jwtTokenService.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        userRepo.save(user);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                jwtTokenService.getExpirationSeconds(),
                user);
    }

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("No authenticated user found");
        }

        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Optional<User> getById(Long id) {
        return userRepo.findById(id);
    }

    public Iterable<User> getAll() {
        return userRepo.findAll();
    }
}
