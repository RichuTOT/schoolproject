package com.example.shetuanlianmeng.service;

import com.example.shetuanlianmeng.entity.Message;
import com.example.shetuanlianmeng.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesByClubId(Long clubId) {
        return messageRepository.findByClubId(clubId);
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
