package com.spot.spotserver.api.user.dto.response;

public record ProfileResponse(
        String profileUrl,
        String color,
        String nickname
) {
}
