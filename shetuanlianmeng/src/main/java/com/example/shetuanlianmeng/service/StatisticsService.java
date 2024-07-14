package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.repository.ClubRepository;
import com.example.shetuanlianmeng.repository.UserRepository;
import com.example.shetuanlianmeng.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public Map<String, Long> getStatistics() {
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("clubCount", clubRepository.count());
        statistics.put("memberCount", userRepository.count()); // Assuming this counts both users and leaders
        statistics.put("activityCount", activityRepository.count());
        return statistics;
    }
}
