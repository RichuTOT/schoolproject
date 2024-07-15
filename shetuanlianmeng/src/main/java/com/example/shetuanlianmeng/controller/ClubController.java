package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.Club;
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

    @GetMapping
    public List<Club> getAllClubs() {
        return clubService.getAllClubs().stream()
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

    @PostMapping("/save")
    public Club saveClub(@RequestBody Club club) {
        return clubService.saveClub(club);
    }

    @PostMapping
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        Club savedClub = clubService.saveClub(club);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClub);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClub(@PathVariable Long id) {
        try {
            clubService.deleteClubAndApplications(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败: " + e.getMessage());
        }
    }
}
