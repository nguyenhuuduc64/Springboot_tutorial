package com.service;

import com.dto.request.AIRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AIService {
    private ChatClient chatClient;
    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String analyzeTech(AIRequest aiRequest) throws ParseException {
        return chatClient.prompt(aiRequest.getMessage()).call().content();
    }
}
