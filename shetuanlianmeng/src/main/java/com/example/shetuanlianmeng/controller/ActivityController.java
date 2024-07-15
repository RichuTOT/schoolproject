package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.config.FileStorageProperties;
import com.example.shetuanlianmeng.entity.Activity;
import com.example.shetuanlianmeng.entity.Image;
import com.example.shetuanlianmeng.repository.ActivityRepository;
import com.example.shetuanlianmeng.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Path uploadPath = Paths.get(fileStorageProperties.getUploadDir());
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();
            
            Map<String, String> response = new HashMap<>();
            response.put("url", fileDownloadUri);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "File upload failed: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        activity.setStatus("pending"); // 设置活动状态为待审核
        Activity savedActivity = activityRepository.save(activity);
        for (String imageUrl : activity.getImages()) {
            Image image = new Image();
            image.setActivity(savedActivity);
            image.setImageUrl(imageUrl);
            imageRepository.save(image);
        }
        return ResponseEntity.ok(savedActivity);
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityRepository.findByStatus("approved");
        for (Activity activity : activities) {
            List<Image> images = imageRepository.findByActivity(activity);
            activity.setImages(images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Activity>> getPendingActivities() {
        List<Activity> activities = activityRepository.findByStatus("pending");
        for (Activity activity : activities) {
            List<Image> images = imageRepository.findByActivity(activity);
            activity.setImages(images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Activity>> getApprovedActivities() {
        List<Activity> activities = activityRepository.findByStatus("approved");
        for (Activity activity : activities) {
            List<Image> images = imageRepository.findByActivity(activity);
            activity.setImages(images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(activities);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveActivity(@PathVariable Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("活动未找到"));
        activity.setStatus("approved");
        activityRepository.save(activity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectActivity(@PathVariable Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("活动未找到"));
        activity.setStatus("rejected");
        activityRepository.save(activity);
        return ResponseEntity.ok().build();
    }
}
