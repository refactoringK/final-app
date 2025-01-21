package com.example.finalapp.service;

import com.example.finalapp.req.ChatHistory;
import com.example.finalapp.req.OpenAiMessage;
import com.example.finalapp.req.OpenAiRequest;
import com.example.finalapp.resp.OpenAiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiChatService {
    @Value("${api.open-ai.key}")
    private String openAiKey;

    public String sendMessage(String message) {
        String defaultContent = """
                너는 우리 웹 사이트 최고의 상담사야
                우리 사이트의 주요 서비스는 다음과 같아.
                1. 자유 게시판 : 강아지와 고양이 등 반려 동물에 대한 정보를 공유하는 커뮤니티
                2. QnA 게시판 : 반려동물과 주요 이벤트에 관련된 질문과 답변하는 게시판
                3. 중고 게시판 : 사용자 간 필요한 물건들을 중고로 싼 가격에 거래하는 게시판
                우리 회사의 주요 정보는 다음과 같아.
                1. 회사 이름 : 댕냥컴퍼니
                2. 회사 전화번호 : 02-1234-1234
                3. 회사 주소 : 서울시 노원구 노원동 11-22
                
                너는 정확한 정보를 기반으로 답변을 해야하고, 답변하기 모호하다면
                우리 회사 전화번호를 안내해줘야 해
                """;

        OpenAiMessage developerMessage = new OpenAiMessage("developer", defaultContent);
        List<OpenAiMessage> messages = new ArrayList<>();
        messages.add(developerMessage);

        OpenAiMessage userMessage = new OpenAiMessage("user", message);
        messages.add(userMessage);

        OpenAiRequest openAiRequest = new OpenAiRequest(messages);

        RestClient restClient = RestClient.builder()
                .baseUrl("https://api.openai.com")
                .build();

        OpenAiResponse openAiResponse = restClient.post()
                .uri("/v1/chat/completions")
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setBearerAuth(openAiKey);
                })
                .body(openAiRequest)
                .retrieve()
                .body(OpenAiResponse.class);

        System.out.println("openAiResponse = " + openAiResponse);

        return openAiResponse.getChoices().getLast().getMessage().getContent();
    }

    public String sendMessage2(HttpSession session, String message) {
        // 세션에서 채팅 기록 가져오기
        ChatHistory chatHistory = (ChatHistory) session.getAttribute("chatHistory");

        // 히스토리가 없다면 (첫 채팅이라면)
        if (chatHistory == null) {
            // 채팅 내역을 생성
            chatHistory = new ChatHistory();
            // 기본 developer 메세지 추가
            chatHistory.addMessage("developer", getDeveloperMessage());
        }

        chatHistory.addMessage("user", message);

        OpenAiRequest openAiRequest = new OpenAiRequest(chatHistory.getMessages());

        RestClient restClient = RestClient.builder()
                .baseUrl("https://api.openai.com")
                .build();

        OpenAiResponse openAiResponse = restClient.post()
                .uri("/v1/chat/completions")
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setBearerAuth(openAiKey);
                })
                .body(openAiRequest)
                .retrieve()
                .body(OpenAiResponse.class);

        System.out.println("openAiResponse = " + openAiResponse);

        // 챗봇의 대답 컨텐츠를 가져옴
        String assistantContent = openAiResponse.getChoices().getLast().getMessage().getContent();
        // 챗봇의 컨텐츠를 채팅 내역에 추가
        chatHistory.addMessage("assistant", assistantContent);

        // 세션에 채팅 내역 저장
        session.setAttribute("chatHistory", chatHistory);

        return assistantContent;
    }

    private String getDeveloperMessage() {
        return """
                너는 우리 웹 사이트 최고의 상담사야
                우리 사이트의 주요 서비스는 다음과 같아.
                1. 자유 게시판 : 강아지와 고양이 등 반려 동물에 대한 정보를 공유하는 커뮤니티
                2. QnA 게시판 : 반려동물과 주요 이벤트에 관련된 질문과 답변하는 게시판
                3. 중고 게시판 : 사용자 간 필요한 물건들을 중고로 싼 가격에 거래하는 게시판
                우리 회사의 주요 정보는 다음과 같아.
                1. 회사 이름 : 댕냥컴퍼니
                2. 회사 전화번호 : 02-1234-1234
                3. 회사 주소 : 서울시 노원구 노원동 11-22
                
                너는 정확한 정보를 기반으로 답변을 해야하고, 답변하기 모호하다면
                우리 회사 전화번호를 안내해줘야 해
                """;
    }
}














