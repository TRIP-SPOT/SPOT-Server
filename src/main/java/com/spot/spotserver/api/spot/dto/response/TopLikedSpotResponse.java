package com.spot.spotserver.api.spot.dto.response;

import com.spot.spotserver.api.spot.domain.Spot;

public record TopLikedSpotResponse(
        String contentId,
        String name,
        Integer region,
        Integer city,
        Long workId,
        String workName,
        String posterUrl,
        Boolean isLiked,
        Integer likeCount
) {
    public static TopLikedSpotResponse fromEntity(Spot spot, Boolean isLiked, Integer likeCount) {
        return new TopLikedSpotResponse(
                spot.getContentId().toString(),
                spot.getName(),
                spot.getRegion() != null ? spot.getRegion().ordinal() : null,
                spot.getCity() != null ? spot.getCity().ordinal() : null,
                spot.getWork() != null ? spot.getWork().getId() : null,
                spot.getWork() != null ? spot.getWork().getName() : null,
                spot.getWork() != null ? spot.getWork().getPosterUrl() : null,
                isLiked,
                likeCount
        );
    }
}

