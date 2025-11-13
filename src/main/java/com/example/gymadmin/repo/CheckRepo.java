package com.example.gymadmin.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gymadmin.model.Check;
import com.example.gymadmin.model.CheckStatus;

public interface CheckRepo extends JpaRepository<Check, String> {
    Optional<Check> findByUserIdAndStatus(Long userId, CheckStatus status);

    long countByStatus(CheckStatus status);
}
