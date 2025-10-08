package com.example.gymadmin.repo;

import com.example.gymadmin.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberRepo extends JpaRepository<Member, String> {
    List<Member> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String first, String last, String email);
}
