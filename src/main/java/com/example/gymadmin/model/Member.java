package com.example.gymadmin.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String membership; // Básico, Premium, Estudiante
    private String status; // activo, retirado, sin pagar
    private String phone;

    // 🔹 Separa fecha y hora de registro
    private LocalDate joinDate;
    private LocalTime joinHour;

    private LocalDate birthday;
    private String emergencyContact;
    private Integer height; // cm
    private Double weight; // kg
    private String avatar;

    @ElementCollection
    private List<String> goals;

    // 🧩 Este método se ejecuta justo antes de insertar el registro
    @PrePersist
    protected void onCreate() {
        this.joinDate = LocalDate.now(); // fecha actual
        this.joinHour = LocalTime.now(); // hora actual
    }

    // Getters y Setters ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    public String getId() {
        return id;
    }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }

    public String getLastName() { return lastName; }
    public void setLastName(String v) { this.lastName = v; }

    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }

    public String getMembership() { return membership; }
    public void setMembership(String v) { this.membership = v; }

    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }

    public String getPhone() { return phone; }
    public void setPhone(String v) { this.phone = v; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate v) { this.joinDate = v; }

    public LocalTime getJoinHour() { return joinHour; }
    public void setJoinHour(LocalTime joinHour) { this.joinHour = joinHour; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate v) { this.birthday = v; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String v) { this.emergencyContact = v; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer v) { this.height = v; }

    public Double getWeight() { return weight; }
    public void setWeight(Double v) { this.weight = v; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String v) { this.avatar = v; }

    public List<String> getGoals() { return goals; }
    public void setGoals(List<String> v) { this.goals = v; }

    
}
