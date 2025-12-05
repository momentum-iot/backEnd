package com.example.gymadmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<?> checkIn(@RequestParam("code") String code) {
        try {
            return ResponseEntity.ok(checkService.checkIn(code));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(403).body(ex.getMessage());
        }
    }

    @PostMapping("/out")
    public ResponseEntity<?> checkOut(@RequestParam("code") String code) {
        try {
            return ResponseEntity.ok(checkService.checkOut(code));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(403).body(ex.getMessage());
        }
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
