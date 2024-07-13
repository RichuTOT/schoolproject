package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.ClubApplication;
import com.example.shetuanlianmeng.repository.ClubApplicationRepository;
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

    private final Path root = Paths.get("uploads");

    public List<ClubApplication> getAllClubApplications() {
        return clubApplicationRepository.findAll();
    }

    public ClubApplication createClubApplication(ClubApplication clubApplication) {
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
}
