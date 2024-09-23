package com.spot.spotserver.api.spot.dto.response;

import com.spot.spotserver.api.spot.domain.Spot;

public record SpotSearchResponse(
        Long id,
        Integer contentId,
        String name,
        Integer region,
        Integer city,
        Long workId,
        String workName,
        String posterUrl,
        String quote,
        Boolean isLiked,
        Integer likeCount
) {
    public static SpotSearchResponse fromEntity(Spot spot, Boolean isLiked, Integer likeCount) {
        return new SpotSearchResponse(
                spot.getId(),
                spot.getContentId(),
                spot.getName(),
                spot.getRegion().ordinal(),
                spot.getCity().ordinal(),
                spot.getWork().getId(),
                spot.getWork().getName(),
                spot.getWork().getPosterUrl(),
                spot.getWork().getQuote(),
                isLiked,
                likeCount
        );
    }
}
