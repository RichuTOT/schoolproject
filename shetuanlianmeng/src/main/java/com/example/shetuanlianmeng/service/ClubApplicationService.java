package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Club;
import com.example.shetuanlianmeng.entity.ClubApplication;
import com.example.shetuanlianmeng.repository.ClubApplicationRepository;
import com.example.shetuanlianmeng.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ClubApplicationService {

    @Autowired
    private ClubApplicationRepository clubApplicationRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserService userService;

    private final Path root = Paths.get("uploads");

    public List<ClubApplication> getAllClubApplications() {
        return clubApplicationRepository.findAll();
    }

    public List<ClubApplication> findByUserId(String userId) {
        return clubApplicationRepository.findByUserId(userId);
    }

    public ClubApplication createClubApplication(ClubApplication clubApplication) {
        List<ClubApplication> existingApplications = findByUserId(clubApplication.getUserId());
        for (ClubApplication existingApplication : existingApplications) {
            if ("pending".equals(existingApplication.getStatus())) {
                throw new IllegalStateException("You already have a pending application.");
            }
        }
        return clubApplicationRepository.save(clubApplication);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
        Path path = root.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), path);
        return path.toString();
    }

    public void approveClubApplication(Long id) {
        ClubApplication application = clubApplicationRepository.findById(id).orElseThrow(() -> new IllegalStateException("Application not found"));
        application.setStatus("approved");
        clubApplicationRepository.save(application);

        // 保存到Club数据库表中
        Club club = new Club();
        club.setName(application.getClubName());
        club.setAuthor(application.getPublisher());
        club.setDate(application.getApplyTime());
        club.setCategory(application.getCategory());
        club.setUserId(Long.valueOf(application.getUserId())); // 确保 userId 被传递
        clubRepository.save(club);

        // 更新用户角色
        userService.updateUserRole(Long.valueOf(application.getUserId()), "clubleader");
    }

    public void rejectClubApplication(Long id) {
        ClubApplication application = clubApplicationRepository.findById(id).orElseThrow(() -> new IllegalStateException("Application not found"));
        application.setStatus("rejected");
        clubApplicationRepository.save(application);
    }
}
