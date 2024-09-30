package com.spot.spotserver.api.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenResponse of(
            final String accessToken,
            final String refreshToken
    ) {
        return new TokenResponse(accessToken, refreshToken);
    }
}