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
import java.util.Optional;

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
        application.setStatus("pending"); // 设置默认状态为pending
        return applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public List<Application> getApplicationsByUserId(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    public List<Application> getApplicationsWithUserInfo(Long currentUserId) {
        Optional<Club> userClubOptional = clubRepository.findByUserId(currentUserId);
        if (userClubOptional.isEmpty()) {
            return List.of(); // 当前用户没有加入任何社团，返回空列表
        }

        Club userClub = userClubOptional.get();
        List<Application> applications = applicationRepository.findByName(userClub.getName());
        return applications.stream().map(application -> {
            Optional<User> userOptional = userRepository.findById(application.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                application.setName(user.getName());
                application.setCategory(user.getGender());
                application.setStudentId(user.getStudentId()); // 设置studentId
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                application.setFormattedDate(application.getDate().format(formatter)); // 设置格式化日期
            }
            return application;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateApplicationStatus(Long id, String status) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid application Id:" + id));
        application.setStatus(status);
        applicationRepository.save(application);
    }

    @Transactional
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}
