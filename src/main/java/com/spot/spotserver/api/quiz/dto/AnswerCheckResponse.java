package com.spot.spotserver.api.quiz.dto;

import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import lombok.Data;

@Data
public class AnswerCheckResponse {
    private boolean isCorrect;

    private Integer region;

    private Integer city;

    public AnswerCheckResponse(boolean isCorrect, Region region, City city) {
        this.isCorrect = isCorrect;
        this.region = region.ordinal();
        this.city = city.ordinal();
    }
}
