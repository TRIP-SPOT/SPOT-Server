package com.spot.spotserver.api.quiz.dto;

import lombok.Data;

@Data
public class AnswerCheckRequest {
    private Long id;

    private Integer answer;

    public AnswerCheckRequest(Long id, Integer answer) {
        this.id = id;
        this.answer = answer;
    }
}
