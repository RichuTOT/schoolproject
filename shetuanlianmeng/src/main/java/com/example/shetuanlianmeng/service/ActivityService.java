package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Activity;
import com.example.shetuanlianmeng.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public Activity createActivity(Activity activity) {
        activity.setPublishTime(LocalDateTime.now()); // 设置当前时间
        return activityRepository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
