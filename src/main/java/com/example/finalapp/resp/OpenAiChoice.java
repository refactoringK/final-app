package com.example.finalapp.resp;

import com.example.finalapp.req.OpenAiMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OpenAiChoice {
    private Integer index;
    private OpenAiMessage message;
    @JsonProperty("finish_reason")
    private String finishReason;
}














