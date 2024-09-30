package com.spot.spotserver.api.auth.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.spot.spotserver.api.auth.client.KakaoAccount;
import com.spot.spotserver.api.auth.client.KakaoProperties;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUserResponse(
        Long id,
        String connected_at,
        KakaoProperties properties,
        KakaoAccount kakaoAccount
) {
}
