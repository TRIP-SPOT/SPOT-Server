package com.spot.spotserver.api.auth.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoAccount(
        Boolean profile_nickname_needs_agreement,
        Boolean profile_image_needs_agreement,
        KakaoProfile profile,
        Boolean has_email,
        Boolean email_needs_agreement,
        Boolean is_email_valid,
        Boolean is_email_verified,
        String email
) {
}
