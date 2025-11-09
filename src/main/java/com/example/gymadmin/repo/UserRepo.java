package com.example.gymadmin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gymadmin.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
