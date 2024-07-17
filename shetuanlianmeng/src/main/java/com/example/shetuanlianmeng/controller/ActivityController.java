package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.config.FileStorageProperties;
import com.example.shetuanlianmeng.entity.Activity;
import com.example.shetuanlianmeng.entity.Image;
import com.example.shetuanlianmeng.repository.ActivityRepository;
import com.example.shetuanlianmeng.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ActivityController {

    private final Path fileStorageLocation;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ImageRepository imageRepository;

    public ActivityController(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

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
        activity.setStatus("pending");
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
        List<Activity> activities = activityRepository.findAll();
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Activity>> getActivitiesByUserId(@PathVariable Long userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
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

    @GetMapping("/approved")
    public ResponseEntity<List<Activity>> getApprovedActivities() {
        List<Activity> activities = activityRepository.findByStatus("approved");
        for (Activity activity : activities) {
            List<Image> images = imageRepository.findByActivity(activity);
            activity.setImages(images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(activities);
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectActivity(@PathVariable Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("活动未找到"));
        activity.setStatus("rejected");
        activityRepository.save(activity);
        return ResponseEntity.ok().build();
    }
}
