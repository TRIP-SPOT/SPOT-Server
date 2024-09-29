package com.spot.spotserver.api.badge.dto.request;

import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BadgeRequest {
    private Region region;

    private City city;

    public void setRegion(int region) {
        this.region = Region.values()[region];
    }

    public void setCity(int city) {
        this.city = City.values()[city];
    }
}
