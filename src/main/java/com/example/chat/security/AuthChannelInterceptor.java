package com.example.chat.security;

import com.example.chat.util.JwtUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.List;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) return message;
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> auth = accessor.getNativeHeader("Authorization");
            String token = (auth != null && !auth.isEmpty()) ? auth.get(0) : null;
            if (token != null && token.startsWith("Bearer ")) token = token.substring(7);
            if (token != null && JwtUtil.validateToken(token)) {
                String username = JwtUtil.getUsername(token);
                Principal principal = new UsernamePasswordAuthenticationToken(username, null, null);
                accessor.setUser(principal);
            } else {
                throw new IllegalArgumentException("Unauthorized: invalid token");
            }
        }
        return message;
    }
}
