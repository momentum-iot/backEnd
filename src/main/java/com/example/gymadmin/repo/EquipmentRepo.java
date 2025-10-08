package com.example.gymadmin.repo;

import com.example.gymadmin.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepo extends JpaRepository<Equipment, String> { }
