package com.example.chat.repository;

import com.example.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop100ByConversationIdOrderByCreatedAtDesc(String conversationId);
}
