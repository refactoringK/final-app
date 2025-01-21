package com.example.finalapp.controller.chat;

import com.example.finalapp.req.ChatHistory;
import com.example.finalapp.req.ClientChatRequest;
import com.example.finalapp.service.AiChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenAiRestController {
    private final AiChatService aiChatService;

    @PostMapping("/chat")
    public String chat(@RequestBody ClientChatRequest chatRequest) {
        return aiChatService.sendMessage(chatRequest.getMessage());
    }

    @PostMapping("/chat2")
    public String chat2(@RequestBody ClientChatRequest chatRequest,
                        HttpSession session) {

        String assistantContent = aiChatService.sendMessage2(session, chatRequest.getMessage());

        ChatHistory chatHistory = (ChatHistory) session.getAttribute("chatHistory");
        System.out.println("chatHistory = " + chatHistory);

        return assistantContent;
    }
}













