package com.example.gymadmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymadmin.service.CheckService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Check", description = "Endpoints for check-in and check-out")
@RestController
@RequestMapping("/api/check")
public class CheckController {

    private final CheckService checkService;

    public CheckController(CheckService checkService) {
        this.checkService = checkService;
    }

    @PostMapping("/in")
    public ResponseEntity<?> checkIn() {
        return ResponseEntity.ok(checkService.checkIn());
    }

    @PostMapping("/out")
    public ResponseEntity<?> checkOut() {
        return ResponseEntity.ok(checkService.checkOut());
    }

    @GetMapping("/concurrency")
    public ResponseEntity<?> getConcurrency() {
        return ResponseEntity.ok(checkService.getConcurrency());
    }

    @GetMapping("/status")
    public ResponseEntity<?> userStatus() {
        boolean inside = checkService.isUserInside();
        return ResponseEntity.ok(inside);
    }
}
