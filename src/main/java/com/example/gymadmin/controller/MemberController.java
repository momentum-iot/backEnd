package com.example.gymadmin.controller;

import com.example.gymadmin.model.Member;
import com.example.gymadmin.repo.PaymentRepo;
import com.example.gymadmin.repo.CheckInRepo;
import com.example.gymadmin.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService service;
    private final PaymentRepo paymentRepo;
    private final CheckInRepo checkInRepo;

    public MemberController(MemberService service, PaymentRepo paymentRepo, CheckInRepo checkInRepo) {
        this.service = service;
        this.paymentRepo = paymentRepo;
        this.checkInRepo = checkInRepo;
    }

    @GetMapping
    public List<Member> list(@RequestParam(value = "q", required = false) String q) {
        if (q != null && !q.isBlank())
            return service.search(q);
        return service.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> get(@PathVariable("id") String id) {
        return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Member create(@RequestBody Member m) {
        return service.save(m);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable String id, @RequestBody Member m) {
        return service.get(id).map(existing -> {
            m.setId(existing.getId());
            return ResponseEntity.ok(service.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        if (service.get(id).isEmpty())
            return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/payments")
    public ResponseEntity<?> payments(@PathVariable String id) {
        return service.get(id).map(m -> ResponseEntity.ok(paymentRepo.findByMemberId(id)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/checkins")
    public ResponseEntity<?> checkins(@PathVariable String id) {
        return service.get(id).map(m -> ResponseEntity.ok(checkInRepo.findByMemberId(id)))
                .orElse(ResponseEntity.notFound().build());
    }
}
