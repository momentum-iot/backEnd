package com.example.gymadmin.controller;

import com.example.gymadmin.model.Equipment;
import com.example.gymadmin.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "*") // permite acceso desde el frontend
public class EquipmentController {

    private final EquipmentService service;

    public EquipmentController(EquipmentService service) {
        this.service = service;
    }

    // 🔹 GET: lista de equipos
    @GetMapping
    public List<Equipment> getAll() {
        return service.findAll();
    }

    // 🔹 GET: un equipo por id
    @GetMapping("/{id}")
    public Equipment getById(@PathVariable String id) {
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    // 🔹 POST: crear nuevo equipo
    @PostMapping
    public Equipment create(@RequestBody Equipment equipment) {
        return service.save(equipment);
    }

    // 🔹 PUT: actualizar equipo existente
    @PutMapping("/{id}")
    public Equipment update(@PathVariable String id, @RequestBody Equipment equipment) {
        return service.update(id, equipment);
    }

    // 🔹 DELETE: eliminar equipo
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
