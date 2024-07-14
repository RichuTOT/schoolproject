package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.ClubApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClubApplicationRepository extends JpaRepository<ClubApplication, Long> {
    List<ClubApplication> findByUserId(String userId); // Updated to return a list of ClubApplications
}
