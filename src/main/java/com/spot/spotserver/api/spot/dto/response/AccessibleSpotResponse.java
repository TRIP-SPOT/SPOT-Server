package com.spot.spotserver.api.spot.dto.response;

import com.spot.spotserver.api.spot.domain.Spot;
import lombok.Data;

@Data
public class AccessibleSpotResponse {
    private String workName;

    private String spotName;

    private String imageUrl;

    private String address;

    private Long quizId;

    public AccessibleSpotResponse(Spot spot, Long quizId) {
        this.workName = spot.getWork().getName();
        this.spotName = spot.getName();
        this.imageUrl = spot.getImageUrl();
        this.address = spot.getAddress();
        this.quizId = quizId;
    }
}
