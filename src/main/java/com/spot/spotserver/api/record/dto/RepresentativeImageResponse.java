package com.spot.spotserver.api.record.dto;

import com.spot.spotserver.api.record.domain.Region;
import com.spot.spotserver.api.record.domain.RepresentativeImage;
import lombok.Data;

@Data
public class RepresentativeImageResponse {
    private Region region;

    private String url;

    public RepresentativeImageResponse(RepresentativeImage representativeImage) {
        this.region = representativeImage.getRegion();
        this.url = representativeImage.getUrl();
    }
}
