package com.spot.spotserver.api.auth.dto.request;

public record TokenRequest(
        String refreshToken
) {
}