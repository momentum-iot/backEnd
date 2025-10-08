package com.example.gymadmin.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String memberId;
    private LocalDate dueDate;
    private Double amount;
    private String status; // pendiente, programado, pagado

    public String getId(){return id;}
    public void setId(String id){this.id=id;}
    public String getMemberId(){return memberId;}
    public void setMemberId(String v){this.memberId=v;}
    public LocalDate getDueDate(){return dueDate;}
    public void setDueDate(LocalDate v){this.dueDate=v;}
    public Double getAmount(){return amount;}
    public void setAmount(Double v){this.amount=v;}
    public String getStatus(){return status;}
    public void setStatus(String v){this.status=v;}
}
