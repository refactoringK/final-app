package com.example.finalapp.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class OpenAiRequest {
    private String model;
    private List<OpenAiMessage> messages;

    public OpenAiRequest(List<OpenAiMessage> messages) {
        this("gpt-3.5-turbo", messages);
    }

    public OpenAiRequest(String model, List<OpenAiMessage> messages) {
        this.model = model;
        this.messages = messages;
    }
}












