package com.spot.spotserver.api.auth.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoProfile(
        String nickname,
        String thumbnail_image_url,
        String profile_image_url,
        Boolean is_default_image,
        Boolean is_default_nickname
) {
}
