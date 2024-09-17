package com.spot.spotserver.api.quiz.dto;

import lombok.Data;

@Data
public class AnswerCheckResponse {
    private boolean isCorrect;

    public AnswerCheckResponse(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
