package com.example.gymadmin.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String status; // operativo, dañado, en mantenimiento
    private LocalDate nextService;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String v) {
        this.name = v;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String v) {
        this.status = v;
    }

    public LocalDate getNextService() {
        return nextService;
    }

    public void setNextService(LocalDate v) {
        this.nextService = v;
    }
}
