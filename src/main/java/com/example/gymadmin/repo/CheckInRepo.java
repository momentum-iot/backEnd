package com.example.gymadmin.repo;

import com.example.gymadmin.model.CheckIn;
import com.example.gymadmin.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CheckInRepo extends JpaRepository<CheckIn, String> {

    List<CheckIn> findByUser(User user);

    void deleteByUser(User user);
}
