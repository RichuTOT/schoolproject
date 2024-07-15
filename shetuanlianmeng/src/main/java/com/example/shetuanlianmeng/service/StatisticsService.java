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
        Map<String, Long> stats = new HashMap<>();
        stats.put("clubCount", clubRepository.count());
        stats.put("memberCount", userRepository.countByRole("member") + userRepository.countByRole("clubleader"));
        stats.put("activityCount", activityRepository.count());
        return stats;
    }
}
