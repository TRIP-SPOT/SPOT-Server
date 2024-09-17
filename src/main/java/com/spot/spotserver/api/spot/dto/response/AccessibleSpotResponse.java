package com.spot.spotserver.api.spot.dto.response;

import com.spot.spotserver.api.spot.domain.Spot;
import lombok.Data;

@Data
public class AccessibleSpotResponse {
    private String workName;

    private String spotName;

    private String imageUrl;

    private String address;

    public AccessibleSpotResponse(Spot spot) {
        this.workName = spot.getWork().getName();
        this.spotName = spot.getName();
        this.imageUrl = spot.getImageUrl();
        this.address = spot.getAddress();
    }
}
