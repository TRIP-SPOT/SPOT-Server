package com.spot.spotserver.api.badge.dto.response;

import com.spot.spotserver.api.badge.domain.Badge;

import java.time.LocalDate;

public record BadgeAcquisitionResponse(
        Integer region,
        Integer city,
        Integer acquisitionType,
        LocalDate createdAt
) {
    public static BadgeAcquisitionResponse fromEntity(Badge badge) {
        return new BadgeAcquisitionResponse(
                badge.getRegion().ordinal(),
                badge.getCity().ordinal(),
                badge.getAcquisitionType().ordinal(),
                badge.getCreatedAt().toLocalDate()
        );
    }
}
