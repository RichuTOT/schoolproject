package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.Message;
import com.example.shetuanlianmeng.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<Message> getMessages(@RequestParam Long clubId) {
        if (clubId == 9999) {
            return messageRepository.findByClubId(9999L);
        }
        return messageRepository.findByClubId(clubId);
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest messageRequest) {
        Message message = new Message();
        message.setMessage(messageRequest.getMessage());
        message.setUserId(messageRequest.getUserId());
        message.setUsername(messageRequest.getUsername());
        message.setClubId(messageRequest.getClubId());
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        return ResponseEntity.ok(message);
    }
}
