package com.spot.spotserver.api.quiz.dto;

import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import lombok.Data;

@Data
public class QuizResponse {
    private Long id;

    private Integer region;
    
    private Integer city;

    private String question;

    private String choice1;

    private String choice2;

    private String choice3;

    private String choice4;

    public QuizResponse(Quiz quiz) {
        this.id = quiz.getId();
        this.region = quiz.getRegion().ordinal();
        this.city = quiz.getCity().ordinal();
        this.question = quiz.getQuestion();
        this.choice1 = quiz.getChoice1();
        this.choice2 = quiz.getChoice2();
        this.choice3 = quiz.getChoice3();
        this.choice4 = quiz.getChoice4();
    }
}
