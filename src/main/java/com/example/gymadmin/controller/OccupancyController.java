package com.example.gymadmin.controller;

import com.example.gymadmin.model.Occupancy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.OffsetDateTime;

@RestController
public class OccupancyController {
    @GetMapping("/api/occupancy")
    public Occupancy get(){
        return new Occupancy(120, 84, OffsetDateTime.now());
    }
}
