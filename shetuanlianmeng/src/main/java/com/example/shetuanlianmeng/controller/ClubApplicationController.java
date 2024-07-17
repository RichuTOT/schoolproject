package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.ClubApplication;
import com.example.shetuanlianmeng.service.ClubApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/club-applications")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ClubApplicationController {

    @Autowired
    private ClubApplicationService clubApplicationService;

    @GetMapping
    public List<ClubApplication> getAllClubApplications() {
        return clubApplicationService.getAllClubApplications();
    }

    @GetMapping("/user")
    public List<ClubApplication> getClubApplicationsByUserId(@RequestParam("userId") String userId) {
        return clubApplicationService.findByUserId(userId);
    }

    @GetMapping("/name/{clubName}")
    public ResponseEntity<List<ClubApplication>> getClubApplicationsByClubName(@PathVariable String clubName) {
        List<ClubApplication> applications = clubApplicationService.findByClubName(clubName);
        if (applications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(applications);
    }


    @PostMapping
    public ResponseEntity<?> createClubApplication(@RequestBody ClubApplication clubApplication) {
        try {
            return ResponseEntity.ok(clubApplicationService.createClubApplication(clubApplication));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return clubApplicationService.uploadImage(file);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveClubApplication(@PathVariable Long id) {
        try {
            clubApplicationService.approveClubApplication(id);
            return ResponseEntity.ok("Approved");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectClubApplication(@PathVariable Long id) {
        try {
            clubApplicationService.rejectClubApplication(id);
            return ResponseEntity.ok("Rejected");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
