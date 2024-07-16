package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.Message;
import com.example.shetuanlianmeng.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getMessagesByClubId(@RequestParam Long clubId) {
        return messageService.getMessagesByClubId(clubId);
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }
}
