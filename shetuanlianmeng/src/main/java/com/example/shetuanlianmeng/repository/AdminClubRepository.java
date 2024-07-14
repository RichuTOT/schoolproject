package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.AdminClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminClubRepository extends JpaRepository<AdminClub, Long> {
    List<AdminClub> findByClubId(Long clubId);
}
