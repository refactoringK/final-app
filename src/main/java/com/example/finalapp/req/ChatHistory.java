package com.example.finalapp.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class ChatHistory {
    private List<OpenAiMessage> messages = new ArrayList<OpenAiMessage>();

    public void addMessage(String role, String message) {
        messages.add(new OpenAiMessage(role, message));

        // 메세지가 너무 많아지면 최근 10개만 유지(세션은 용량이 커지면 서버에 부담이 큼)
        if (messages.size() > 10) {
            // 0번째 메세지는 developer 메세지이기 때문에 삭제하면 안됨!
            messages.remove(1);
        }
    }
}














