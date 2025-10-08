package com.example.gymadmin.repo;

import com.example.gymadmin.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CheckInRepo extends JpaRepository<CheckIn, String> {
    List<CheckIn> findByMemberId(String memberId);
    void deleteByMemberId(String memberId);
}
