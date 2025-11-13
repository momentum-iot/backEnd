package com.example.gymadmin.service;

import java.util.List;
import java.util.Optional;

import com.example.gymadmin.model.Membership;
import com.example.gymadmin.model.Status;
import com.example.gymadmin.model.User;
import com.example.gymadmin.repo.PaymentRepo;
import com.example.gymadmin.repo.UserRepo;

import jakarta.transaction.Transactional;

public class UserService {

    private final UserRepo userRepo;
    private final PaymentRepo paymentRepo;

    public UserService (UserRepo userRepo, PaymentRepo paymentRepo) {
        this.userRepo = userRepo;
        this.paymentRepo = paymentRepo;
        
    }

    public List<User> list() {
        return  userRepo.findAll();
    }

    public Optional<User> getById(Long id) {
        return userRepo.findById(id);
    }

    public User save(User user) {
        if (user.getStatus() == null) {
            user.setStatus(Status.ACTIVO);
        }
        if (user.getMembership() == null) {
            user.setMembership(Membership.BASICO);
        }
        return userRepo.save(user);
    }

    @Transactional
    public void delete(Long id) {
        
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) return;

        User user = optionalUser.get();

        paymentRepo.deleteByUser(user);
        
        userRepo.delete(user);
    }

    public List<User> search(String q) {
        return userRepo
                .findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q, q);
    }

}
