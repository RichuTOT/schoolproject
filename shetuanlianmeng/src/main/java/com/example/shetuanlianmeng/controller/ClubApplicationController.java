package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.ClubApplication;
import com.example.shetuanlianmeng.service.ClubApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/club-applications")
public class ClubApplicationController {

    @Autowired
    private ClubApplicationService clubApplicationService;

    @PostMapping("/submit")
    public ResponseEntity<ClubApplication> submitApplication(@RequestBody ClubApplication application) {
        return new ResponseEntity<>(clubApplicationService.submitApplication(application), HttpStatus.CREATED);
    }

    @PostMapping("/review/{id}")
    public ResponseEntity<ClubApplication> reviewApplication(@PathVariable Long id, @RequestParam String status) {
        return new ResponseEntity<>(clubApplicationService.reviewApplication(id, status), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClubApplication>> getAllApplications() {
        return new ResponseEntity<>(clubApplicationService.getAllApplications(), HttpStatus.OK);
    }
}
