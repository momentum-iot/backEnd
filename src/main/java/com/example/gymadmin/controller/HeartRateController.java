package com.example.gymadmin.controller;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymadmin.model.HeartRate;
import com.example.gymadmin.service.HeartRateService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/heart-rate")
@Tag(name = "Heart Rate", description = "Endpoints for user heart rate")
public class HeartRateController {
    
    private final HeartRateService heartRateService;

    public HeartRateController(HeartRateService heartRateService) {
        this.heartRateService = heartRateService;
    }

    @PostMapping("/{userId}")
    public HeartRate saveHeartRate(
            @PathVariable Long userId,
            @RequestParam Integer bpm) {

        return heartRateService.saveHeartRate(userId, bpm);
    }

    @GetMapping("/{userId}")
    public List<HeartRate> getHeartRateHistory(@PathVariable Long userId) {
        return heartRateService.getHeartRateHistory(userId);
    }
}
