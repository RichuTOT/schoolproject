package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
