package com.example.gymadmin.service;

import com.example.gymadmin.model.Equipment;
import com.example.gymadmin.repo.EquipmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    private final EquipmentRepo repo;

    public EquipmentService(EquipmentRepo repo) {
        this.repo = repo;
    }

    public List<Equipment> findAll() {
        return repo.findAll();
    }

    public Optional<Equipment> findById(String id) {
        return repo.findById(id);
    }

    public Equipment save(Equipment equipment) {
        return repo.save(equipment);
    }

    public Equipment update(String id, Equipment updated) {
        return repo.findById(id).map(e -> {
            e.setName(updated.getName());
            e.setStatus(updated.getStatus());
            e.setNextService(updated.getNextService());
            return repo.save(e);
        }).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
