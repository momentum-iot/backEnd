package com.example.gymadmin.controller;

import com.example.gymadmin.model.CheckIn;
import com.example.gymadmin.repo.CheckInRepo;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/checkins")
public class CheckInController {
    private final CheckInRepo repo;
    public CheckInController(CheckInRepo repo){ this.repo = repo; }
    @GetMapping
    public List<CheckIn> list(){ return repo.findAll(); }
    @PostMapping
    public CheckIn create(@RequestBody CheckIn c){ return repo.save(c); }
}
