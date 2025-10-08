package com.example.gymadmin.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Plan {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Double price;
    private String duration; // mensual
    @ElementCollection
    private List<String> perks;

    public String getId(){return id;}
    public void setId(String id){this.id=id;}
    public String getName(){return name;}
    public void setName(String v){this.name=v;}
    public Double getPrice(){return price;}
    public void setPrice(Double v){this.price=v;}
    public String getDuration(){return duration;}
    public void setDuration(String v){this.duration=v;}
    public List<String> getPerks(){return perks;}
    public void setPerks(List<String> v){this.perks=v;}
}
