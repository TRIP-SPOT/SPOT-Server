package com.spot.spotserver.api.auth.jwt.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
@AllArgsConstructor
@Getter
@Builder
public class Token {

    @Id
    private String id;

    private String refreshToken;

    public static Token of(
            final Long userId,
            final String refreshToken
    ) {
        return Token.builder()
                .id(userId.toString())
                .refreshToken(refreshToken)
                .build();
    }
}