package com.example.gymadmin.service;

import com.example.gymadmin.model.Member;
import com.example.gymadmin.repo.CheckInRepo;
import com.example.gymadmin.repo.MemberRepo;
import com.example.gymadmin.repo.PaymentRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepo memberRepo;
    private final PaymentRepo paymentRepo;
    private final CheckInRepo checkInRepo;

    public MemberService(MemberRepo memberRepo, PaymentRepo paymentRepo, CheckInRepo checkInRepo) {
        this.memberRepo = memberRepo;
        this.paymentRepo = paymentRepo;
        this.checkInRepo = checkInRepo;
    }

    public List<Member> list() {
        return memberRepo.findAll();
    }

    public Optional<Member> get(String id) {
        return memberRepo.findById(id);
    }

    public Member save(Member m) {
        return memberRepo.save(m);
    }
        
    @Transactional
    public void delete(String id) {
        checkInRepo.deleteAll(checkInRepo.findByMemberId(id));
        paymentRepo.deleteAll(paymentRepo.findByMemberId(id));
        memberRepo.deleteById(id);
    }

    public List<Member> search(String q) {
        return memberRepo
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q, q);
    }
}
