package com.spot.spotserver.api.spot.dto.response;

import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.api.spot.domain.Spot;
import lombok.Data;

@Data
public class AccessibleSpotResponse {
    private String workName;

    private String spotName;

    private String imageUrl;

    private Region region;

    private City city;

    private Long quizId;

    public AccessibleSpotResponse(Spot spot, Long quizId) {
        this.workName = spot.getWork().getName();
        this.spotName = spot.getName();
        this.imageUrl = spot.getWork().getPosterUrl();
        this.region = spot.getRegion();
        this.city = spot.getCity();
        this.quizId = quizId;
    }
}
