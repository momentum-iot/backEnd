package com.example.gymadmin.repo;

import com.example.gymadmin.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, String> {
    List<Payment> findByMemberId(String memberId);
    void deleteByMemberId(String memberId);
}
