package com.example.gymadmin.service;

import java.time.OffsetDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.gymadmin.model.Check;
import com.example.gymadmin.model.CheckStatus;
import com.example.gymadmin.model.User;
import com.example.gymadmin.repo.CheckRepo;
import com.example.gymadmin.repo.UserRepo;

@Service
public class CheckService {
    private final CheckRepo checkRepo;
    private final UserRepo userRepo;

    public CheckService(
            CheckRepo checkRepo,
            UserRepo userRepo) {
        this.checkRepo = checkRepo;
        this.userRepo = userRepo;
    }

    private User getLoggedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email).orElseThrow();
    }

    public String checkIn(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Código de acceso requerido");
        }

        User user = getLoggedUser();

        var activeCheck = checkRepo.findByAccessCodeAndStatus(code.trim(), CheckStatus.ACTIVE);
        if (activeCheck.isPresent()) {
            return "Ya estás dentro del gimnasio";
        }

        Check check = new Check();
        check.setUser(user);
        check.setCheckInTime(OffsetDateTime.now());
        check.setStatus(CheckStatus.ACTIVE);
        check.setAccessCode(code.trim());

        checkRepo.save(check);

        return "Check-in registrado";
    }

    public String checkOut(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Código de acceso requerido");
        }

        User user = getLoggedUser();

        var activeCheck = checkRepo.findByAccessCodeAndStatus(code.trim(), CheckStatus.ACTIVE)
                .orElse(null);

        if (activeCheck == null) {
            return "No tienes un check-in activo";
        }

        activeCheck.setCheckOutTime(OffsetDateTime.now());
        activeCheck.setStatus(CheckStatus.ENDED);
        activeCheck.setAccessCode(code.trim());

        checkRepo.save(activeCheck);
        return "Check-out registrado";
    }

    public long getConcurrency() {
        return checkRepo.countByStatus(CheckStatus.ACTIVE);
    }

    public boolean isUserInside() {
        User user = getLoggedUser();
        return checkRepo.findByUserIdAndStatus(user.getId(), CheckStatus.ACTIVE).isPresent();
    }  

}
