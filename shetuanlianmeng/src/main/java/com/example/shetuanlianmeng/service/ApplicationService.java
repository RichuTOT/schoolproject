package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Application;
import com.example.shetuanlianmeng.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application applyToClub(Application application) {
        application.setStatus("PENDING");
        return applicationRepository.save(application);
    }

    public Application reviewApplication(Long id, String status) {
        Application application = applicationRepository.findById(id).orElseThrow();
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // 其他申请相关操作
}
