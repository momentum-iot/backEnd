package com.example.gymadmin.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gymadmin.model.HeartRate;
import com.example.gymadmin.model.User;
import com.example.gymadmin.repo.HeartRateRepo;
import com.example.gymadmin.repo.UserRepo;

@Service
public class HeartRateService {

    private HeartRateRepo heartRateRepo;
    private UserRepo userRepo;

    public HeartRateService(HeartRateRepo heartRateRepo, UserRepo userRepo) {
        this.heartRateRepo = heartRateRepo;
        this.userRepo = userRepo;
    }

    public HeartRate saveHeartRate(Long userId, Integer bpm) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        HeartRate hr = new HeartRate(bpm, LocalDate.now(), LocalTime.now(), user);
        return heartRateRepo.save(hr);

    }

    public List<HeartRate> getHeartRateHistory(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return heartRateRepo.findByUserOrderByDateDescTimeDesc(user);
    }
    
}
