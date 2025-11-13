package com.example.gymadmin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gymadmin.model.HeartRate;
import com.example.gymadmin.model.User;

public interface HeartRateRepo extends JpaRepository<HeartRate, Long> {

    List<HeartRate> findByUserOrderByDateDescTimeDesc(User user);
    
}
