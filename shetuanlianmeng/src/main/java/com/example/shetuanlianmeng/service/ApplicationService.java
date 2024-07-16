package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Application;
import com.example.shetuanlianmeng.entity.Club;
import com.example.shetuanlianmeng.repository.ApplicationRepository;
import com.example.shetuanlianmeng.repository.ClubRepository;
import com.example.shetuanlianmeng.repository.UserRepository;
import com.example.shetuanlianmeng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

     @Autowired
    private ClubRepository clubRepository;

    @Transactional
    public Application saveApplication(Application application) {
        application.setDate(LocalDateTime.now());
        return applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public List<Application> getApplicationsByUserId(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    public List<Application> getApplicationsWithUserInfo(Long currentUserId) {
        Club userClub = clubRepository.findByUserId(currentUserId).orElse(null);
        if (userClub == null) {
            return List.of(); // 当前用户没有加入任何社团，返回空列表
        }

        List<Application> applications = applicationRepository.findByName(userClub.getName());
        return applications.stream().map(application -> {
            User user = userRepository.findById(application.getUserId()).orElse(null);
            if (user != null) {
                application.setName(user.getName());
                application.setCategory(user.getGender());
                application.setStudentId(user.getStudentId()); // 设置studentId
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                application.setFormattedDate(application.getDate().format(formatter)); // 设置格式化日期
            }
            return application;
        }).collect(Collectors.toList());
    }
}
