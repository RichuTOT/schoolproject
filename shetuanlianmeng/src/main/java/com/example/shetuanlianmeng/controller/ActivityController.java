package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.config.FileStorageProperties;
import com.example.shetuanlianmeng.entity.Activity;
import com.example.shetuanlianmeng.entity.Image;
import com.example.shetuanlianmeng.repository.ActivityRepository;
import com.example.shetuanlianmeng.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);
    private final Path fileStorageLocation;

    public ActivityController(FileStorageProperties fileStorageProperties) {
        logger.info("Initializing ActivityController...");
        logger.info("Upload directory from FileStorageProperties: {}", fileStorageProperties.getUploadDir());

        String uploadDir = fileStorageProperties.getUploadDir();
        if (uploadDir == null || uploadDir.isEmpty()) {
            throw new RuntimeException("Upload directory is null or empty. Please check your configuration.");
        }

        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        logger.info("Resolved file storage location: {}", this.fileStorageLocation);

        try {
            Files.createDirectories(this.fileStorageLocation);
            logger.info("Directories created successfully.");
        } catch (Exception ex) {
            logger.error("Could not create the directory where the uploaded files will be stored.", ex);
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        logger.info("Handling file upload...");

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                logger.info("Uploads directory created: {}", uploadPath.toString());
            }

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            logger.info("File uploaded to: {}", filePath.toString());

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();

            Map<String, String> response = new HashMap<>();
            response.put("url", fileDownloadUri);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            logger.error("File upload failed.", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "File upload failed: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        activity.setStatus("审核中"); // 设置活动状态为待审核
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
        List<Activity> activities = activityRepository.findByStatus("审核中");
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
        activity.setStatus("已同意");
        activityRepository.save(activity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectActivity(@PathVariable Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("活动未找到"));
        activity.setStatus("已拒绝");
        activityRepository.save(activity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Activity>> getApprovedActivities() {
        List<Activity> activities = activityRepository.findByStatus("已同意");
        for (Activity activity : activities) {
            List<Image> images = imageRepository.findByActivity(activity);
            activity.setImages(images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(activities);
    }
}
