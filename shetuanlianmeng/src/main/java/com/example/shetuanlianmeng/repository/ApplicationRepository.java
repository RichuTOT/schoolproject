package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUserId(Long userId);
    List<Application> findByName(String name); // 添加这个方法
}
