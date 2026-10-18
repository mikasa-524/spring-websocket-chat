package com.example.chat.redis;

import com.example.chat.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper mapper;

    public RedisMessagePublisher(StringRedisTemplate redisTemplate, ObjectMapper mapper) {
        this.redisTemplate = redisTemplate;
        this.mapper = mapper;
    }

    public void publish(String channel, ChatMessage message) {
        try {
            String payload = mapper.writeValueAsString(message);
            redisTemplate.convertAndSend(channel, payload);
        } catch (Exception e) {
            // log in prod
        }
    }
}
