package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.ClubApplication;
import com.example.shetuanlianmeng.repository.ClubApplicationRepository;
import com.example.shetuanlianmeng.entity.User;
import com.example.shetuanlianmeng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List; // 添加此行

@Service
public class ClubApplicationService {

    @Autowired
    private ClubApplicationRepository clubApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    public ClubApplication submitApplication(ClubApplication application) {
        application.setStatus("PENDING");
        return clubApplicationRepository.save(application);
    }

    public ClubApplication reviewApplication(Long id, String status) {
        ClubApplication application = clubApplicationRepository.findById(id).orElseThrow();
        application.setStatus(status);

        if ("APPROVED".equals(status)) {
            User user = application.getUser();
            user.setRole("CLUB_MANAGER");
            userRepository.save(user);
        }

        return clubApplicationRepository.save(application);
    }

    public List<ClubApplication> getAllApplications() {
        return clubApplicationRepository.findAll();
    }
}
