package com.example.gymadmin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymadmin.dto.PaymentConfirmationRequest;
import com.example.gymadmin.dto.PaymentConfirmationResponse;
import com.example.gymadmin.dto.MembershipCheckoutRequest;
import com.example.gymadmin.dto.PaymentRequest;
import com.example.gymadmin.dto.PaymentResponse;
import com.example.gymadmin.model.Payment;
import com.example.gymadmin.service.AuthService;
import com.example.gymadmin.service.PaymentService;
import com.stripe.exception.StripeException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthService authService;

    public PaymentController(PaymentService paymentService, AuthService authService) {
        this.paymentService = paymentService;
        this.authService = authService;
    }

    @GetMapping
    public List<Payment> list() {
        return paymentService.list();
    }

    @PostMapping("/membership")
    public ResponseEntity<?> createMembership(@Valid @RequestBody MembershipCheckoutRequest request) {
        try {
            var user = authService.getCurrentUser();
            PaymentResponse response = paymentService.createMembershipCheckoutSession(request.getMembership(), user);
            return ResponseEntity.ok(java.util.Map.of(
                    "sessionId", response.getSessionId(),
                    "url", response.getCheckoutUrl()));
        } catch (StripeException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> createCheckout(@Valid @RequestBody PaymentRequest request) {
        try {
            PaymentResponse response = paymentService.createCheckoutSession(request);
            return ResponseEntity.ok(response);
        } catch (StripeException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@Valid @RequestBody PaymentConfirmationRequest request) {
        try {
            PaymentConfirmationResponse response = paymentService.confirmPayment(request);
            return ResponseEntity.ok(response);
        } catch (StripeException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }
}
