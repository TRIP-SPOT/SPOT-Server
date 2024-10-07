package com.spot.spotserver.api.spot.dto.response;

import com.spot.spotserver.api.spot.domain.Spot;
import lombok.Data;

@Data
public class AccessibleSpotWithQuizResponse {
    private String workName;

    private String spotName;

    private String imageUrl;

    private Integer region;

    private Integer city;

    private Long quizId;

    public AccessibleSpotWithQuizResponse(Spot spot, Long quizId) {
        this.workName = spot.getWork().getName();
        this.spotName = spot.getName();
        this.imageUrl = spot.getWork().getPosterUrl();
        this.region = spot.getRegion().ordinal();
        this.city = spot.getCity().ordinal();
        this.quizId = quizId;
    }
}
