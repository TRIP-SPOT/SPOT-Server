package com.spot.spotserver.api.quiz.dto;

import com.spot.spotserver.api.quiz.domain.Quiz;
import lombok.Data;

@Data
public class AnswerCheckResponse {
    private boolean isCorrect;

    private Integer region;

    private Integer city;

    public AnswerCheckResponse(boolean isCorrect, Quiz quiz) {
        this.isCorrect = isCorrect;
        this.region = quiz.getSpot().getRegion().ordinal();
        this.city = quiz.getSpot().getCity().ordinal();
    }
}
