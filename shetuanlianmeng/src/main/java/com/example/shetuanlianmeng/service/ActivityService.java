package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Activity;
import com.example.shetuanlianmeng.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity updateActivity(Long id, Activity activityDetails) {
        Activity activity = activityRepository.findById(id).orElseThrow();
        activity.setName(activityDetails.getName());
        activity.setDescription(activityDetails.getDescription());
        activity.setDate(activityDetails.getDate());
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElseThrow();
    }
}
