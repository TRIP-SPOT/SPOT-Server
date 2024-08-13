package com.spot.spotserver.api.auth.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoProperties(
        String nickname,
        String profile_image,
        String thumbnail_image
) {
}
