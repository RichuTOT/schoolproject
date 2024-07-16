package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.Application;
import com.example.shetuanlianmeng.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application savedApplication = applicationService.saveApplication(application);
        return ResponseEntity.ok(savedApplication);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getApplicationsByUserId(@PathVariable Long userId) {
        List<Application> applications = applicationService.getApplicationsByUserId(userId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/with-user-info")
    public List<Application> getApplicationsWithUserInfo(@RequestParam Long userId) {
        return applicationService.getApplicationsWithUserInfo(userId);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Void> approveApplication(@PathVariable Long id) {
        applicationService.updateApplicationStatus(id, "通过");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<Void> rejectApplication(@PathVariable Long id) {
        applicationService.updateApplicationStatus(id, "拒绝");
        applicationService.deleteApplication(id);
        return ResponseEntity.ok().build();
    }

}
