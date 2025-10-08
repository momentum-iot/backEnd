package com.example.gymadmin.controller;

import com.example.gymadmin.model.Plan;
import com.example.gymadmin.repo.PlanRepo;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanRepo repo;
    public PlanController(PlanRepo repo){ this.repo = repo; }
    @GetMapping
    public List<Plan> list(){ return repo.findAll(); }
}
