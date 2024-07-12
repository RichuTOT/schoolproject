package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Application;
import com.example.shetuanlianmeng.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public Application saveApplication(Application application) {
        application.setDate(LocalDateTime.now());
        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsByUserId(Long userId) {
        return applicationRepository.findByUserId(userId);
    }
}
