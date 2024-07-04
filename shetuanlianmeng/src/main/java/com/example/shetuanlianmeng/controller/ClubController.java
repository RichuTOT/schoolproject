package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.Club;
import com.example.shetuanlianmeng.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @PostMapping("/create")
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        return new ResponseEntity<>(clubService.createClub(club), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs() {
        return new ResponseEntity<>(clubService.getAllClubs(), HttpStatus.OK);
    }

    // 其他社团相关操作
}
