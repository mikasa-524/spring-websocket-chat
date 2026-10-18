package com.example.chat.controller;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.ChatMessageDTO;
import com.example.chat.repository.MessageRepository;
import com.example.chat.redis.RedisMessagePublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;

@Controller
public class ChatController {
    private final MessageRepository repo;
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisMessagePublisher redisPublisher;

    public ChatController(MessageRepository repo, SimpMessagingTemplate messagingTemplate, RedisMessagePublisher redisPublisher) {
        this.repo = repo;
        this.messagingTemplate = messagingTemplate;
        this.redisPublisher = redisPublisher;
    }

    @MessageMapping("/chat.send")
    public void send(Principal principal, ChatMessageDTO dto) {
        ChatMessage m = new ChatMessage();
        m.setConversationId(dto.getConversationId());
        m.setSender(principal != null ? principal.getName() : dto.getSender());
        m.setContent(dto.getContent());
        m.setCreatedAt(Instant.now());
        ChatMessage saved = repo.save(m);

        // 本实例发送
        messagingTemplate.convertAndSend("/topic/conversation." + dto.getConversationId(), saved);
        // 发布到 Redis 通知其他实例
        redisPublisher.publish("chat:" + dto.getConversationId(), saved);
    }
}
