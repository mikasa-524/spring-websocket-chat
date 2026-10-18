package com.example.chat.redis;

import com.example.chat.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageSubscriber implements MessageListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper mapper;

    public RedisMessageSubscriber(SimpMessagingTemplate messagingTemplate, ObjectMapper mapper) {
        this.messagingTemplate = messagingTemplate;
        this.mapper = mapper;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String body = new String(message.getBody());
            ChatMessage m = mapper.readValue(body, ChatMessage.class);
            messagingTemplate.convertAndSend("/topic/conversation." + m.getConversationId(), m);
        } catch (Exception e) {
            // log
        }
    }
}
