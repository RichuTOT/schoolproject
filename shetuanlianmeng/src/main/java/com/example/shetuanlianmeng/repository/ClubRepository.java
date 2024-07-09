package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByNameContaining(String name);
    List<Club> findByCategory(String category);
    boolean existsByName(String name);
}
