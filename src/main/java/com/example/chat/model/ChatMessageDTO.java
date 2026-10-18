package com.example.chat.model;

public class ChatMessageDTO {
    private String conversationId;
    private String content;
    private String sender;
    // getters / setters
    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
}
