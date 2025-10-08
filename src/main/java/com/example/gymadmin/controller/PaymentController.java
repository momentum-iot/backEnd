package com.example.gymadmin.controller;

import com.example.gymadmin.model.Payment;
import com.example.gymadmin.repo.PaymentRepo;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentRepo repo;
    public PaymentController(PaymentRepo repo){ this.repo = repo; }
    @GetMapping
    public List<Payment> list(){ return repo.findAll(); }
}
