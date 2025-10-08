package com.example.gymadmin.repo;

import com.example.gymadmin.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepo extends JpaRepository<Plan, String> { }
