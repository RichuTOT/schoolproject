package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Club;
import com.example.shetuanlianmeng.repository.ClubApplicationRepository;
import com.example.shetuanlianmeng.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubApplicationRepository clubApplicationRepository;

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public List<Club> searchClubs(String name) {
        return clubRepository.findByNameContaining(name);
    }

    public List<Club> getClubsByCategory(String category) {
        return clubRepository.findByCategory(category);
    }

    public Club saveClub(Club club) {
        if (clubRepository.existsByName(club.getName())) {
            throw new IllegalArgumentException("社团名称已存在");
        }
        return clubRepository.save(club);
    }

    @Transactional
    public void deleteClubAndApplications(Long id) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("社团未找到"));
        clubApplicationRepository.deleteByClubName(club.getName());
        clubRepository.deleteById(id);
    }
}
