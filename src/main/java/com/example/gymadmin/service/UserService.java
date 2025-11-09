package com.example.gymadmin.service;

import com.example.gymadmin.model.User;
import com.example.gymadmin.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User register(User user) {
        
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // No devolver la contraseña
                user.setPassword(null);
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }

    public Optional<User> getById(Long id) {
        return userRepo.findById(id);
    }

    public Iterable<User> getAll() {
        return userRepo.findAll();
    }
}