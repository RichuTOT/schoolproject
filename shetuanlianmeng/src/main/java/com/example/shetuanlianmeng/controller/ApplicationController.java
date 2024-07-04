package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.Application;
import com.example.shetuanlianmeng.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<Application> applyToClub(@RequestBody Application application) {
        return new ResponseEntity<>(applicationService.applyToClub(application), HttpStatus.CREATED);
    }

    @PostMapping("/review/{id}")
    public ResponseEntity<Application> reviewApplication(@PathVariable Long id, @RequestParam String status) {
        return new ResponseEntity<>(applicationService.reviewApplication(id, status), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return new ResponseEntity<>(applicationService.getAllApplications(), HttpStatus.OK);
    }
}
