package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.ClubApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubApplicationRepository extends JpaRepository<ClubApplication, Long> {
    List<ClubApplication> findByUserId(String userId);
    void deleteByClubName(String clubName);
    List<ClubApplication> findByClubName(String clubName);
}
