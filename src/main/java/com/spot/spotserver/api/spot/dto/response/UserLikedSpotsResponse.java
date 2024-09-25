package com.spot.spotserver.api.spot.dto.response;

import com.spot.spotserver.api.spot.domain.Spot;

public record UserLikedSpotsResponse(
        Long id,
        String contentId,
        String name,
        Integer region,
        Integer city,
        Long workId,
        String posterUrl
) {
    public static UserLikedSpotsResponse fromEntity(Spot spot) {
        return new UserLikedSpotsResponse(
                spot.getId(),
                spot.getContentId().toString(),
                spot.getName(),
                spot.getRegion() != null ? spot.getRegion().ordinal() : null,
                spot.getCity() != null ? spot.getCity().ordinal() : null,
                spot.getWork() != null ? spot.getWork().getId() : null,
                spot.getWork() != null ? spot.getWork().getPosterUrl() : null
        );
    }
}
