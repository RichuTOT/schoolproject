package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.Club;
import com.example.shetuanlianmeng.repository.ClubRepository;
import com.example.shetuanlianmeng.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubRepository clubRepository;

    @GetMapping
    public List<Club> getAllClubs() {
        return clubRepository.findAll().stream()
                .map(club -> {
                    if (club.getName() == null) {
                        club.setName(""); // 设置默认名称
                    }
                    return club;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<Club> searchClubs(@RequestParam String name) {
        return clubService.searchClubs(name);
    }

    @GetMapping("/category")
    public List<Club> getClubsByCategory(@RequestParam String category) {
        return clubService.getClubsByCategory(category);
    }

    // 修改路径以避免冲突
    @PostMapping("/save")
    public Club saveClub(@RequestBody Club club) {
        return clubService.saveClub(club);
    }

    @PostMapping
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        Club savedClub = clubService.saveClub(club);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClub);
    }
}
