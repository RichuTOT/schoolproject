package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByStatus(String status);
    List<Activity> findByUserId(Long userId); // 添加通过 userId 查找活动的方法
}
