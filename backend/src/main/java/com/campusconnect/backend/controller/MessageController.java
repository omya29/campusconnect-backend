package com.campusconnect.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.backend.entity.Message;
import com.campusconnect.backend.repository.MessageRepository;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    // ✅ SEND MESSAGE
    @PostMapping("/send")
    public String sendMessage(@RequestBody Message message) {

        if (message.getSenderUsername() == null || message.getSenderUsername().isBlank()) {
            return "Sender username required";
        }

        if (message.getReceiverUsername() == null || message.getReceiverUsername().isBlank()) {
            return "Receiver username required";
        }

        if (message.getText() == null || message.getText().isBlank()) {
            return "Message text required";
        }

        messageRepository.save(message);
        return "Message sent";
    }

    // ✅ GET CHAT BETWEEN TWO USERS
    @GetMapping("/chat/{user1}/{user2}")
    public List<Message> getChat(@PathVariable String user1, @PathVariable String user2) {
        return messageRepository.getChat(user1, user2);
    }

    // ✅ GET CHAT LIST (all usernames you chatted with)
    @GetMapping("/chats/{username}")
    public List<String> getChatUsers(@PathVariable String username) {
        return messageRepository.getChatUsers(username);
    }

    // ✅ TEST
    @GetMapping("/test")
    public String test() {
        return "Message API Working";
    }
}
