package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.ClubApplication;
import com.example.shetuanlianmeng.service.ClubApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/club-applications")
@CrossOrigin(origins = "http://localhost:5175", allowCredentials = "true")
public class ClubApplicationController {

    @Autowired
    private ClubApplicationService clubApplicationService;

    @GetMapping
    public List<ClubApplication> getAllClubApplications() {
        return clubApplicationService.getAllClubApplications();
    }

    @PostMapping
    public ClubApplication createClubApplication(@RequestBody ClubApplication clubApplication) {
        return clubApplicationService.createClubApplication(clubApplication);
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return clubApplicationService.uploadImage(file);
    }
}
