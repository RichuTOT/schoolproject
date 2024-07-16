package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByClubId(Long clubId);
}
