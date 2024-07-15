package com.example.shetuanlianmeng.repository;

import com.example.shetuanlianmeng.entity.Activity;
import com.example.shetuanlianmeng.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByActivity(Activity activity);
}
