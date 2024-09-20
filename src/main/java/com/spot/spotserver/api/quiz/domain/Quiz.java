package com.spot.spotserver.api.quiz.domain;

import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "quiz")
@Getter
@NoArgsConstructor
public class Quiz extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Region region;

    private City city;

    private String question;

    private String choice1;

    private String choice2;

    private String choice3;

    private String choice4;

    private Integer answer;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @Builder
    public Quiz(Long id, Region region, City city, String question, String choice1, String choice2, String choice3, String choice4, Integer answer, Spot spot) {
        this.id = id;
        this.region = region;
        this.city = city;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
        this.spot = spot;
    }

    public boolean isCorrect(Integer answer) {
        return Objects.equals(this.answer, answer);
    }
}
