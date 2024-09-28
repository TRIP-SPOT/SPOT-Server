package com.spot.spotserver.api.badge.dto;

import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BadgeRequest {
    private Region region;

    private City city;
}
