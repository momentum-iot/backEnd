package com.example.gymadmin.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gymadmin.dto.AuthResponse;
import com.example.gymadmin.dto.LoginRequest;
import com.example.gymadmin.model.Role;
import com.example.gymadmin.model.User;
import com.example.gymadmin.repo.UserRepo;
import com.example.gymadmin.security.JwtTokenService;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public UserService(UserRepo userRepo,
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
        return new AuthResponse(
                jwtTokenService.generateToken(saved),
                jwtTokenService.getExpirationSeconds(),
                saved);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new AuthResponse(
                jwtTokenService.generateToken(user),
                jwtTokenService.getExpirationSeconds(),
                user);
    }

    public Optional<User> getById(Long id) {
        return userRepo.findById(id);
    }

    public Iterable<User> getAll() {
        return userRepo.findAll();
    }
}
