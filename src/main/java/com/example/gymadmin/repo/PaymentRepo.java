package com.example.gymadmin.repo;

import com.example.gymadmin.model.Payment;
import com.example.gymadmin.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment, String> {

    List<Payment> findByUser(User user);

    void deleteByUser(User user);

    Optional<Payment> findByCheckoutSessionId(String checkoutSessionId);
}
